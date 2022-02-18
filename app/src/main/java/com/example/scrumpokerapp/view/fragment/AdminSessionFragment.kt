package com.example.scrumpokerapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentSessionBinding
import com.example.scrumpokerapp.model.*
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.SessionStoryAdapter
import com.example.scrumpokerapp.view.adapter.UserCardAdapter
import com.example.scrumpokerapp.view.listener.CustomCardItemListener
import com.example.scrumpokerapp.view.listener.CustomStoryItemListener
import com.example.scrumpokerapp.viewmodel.AdminSessionViewModel
import com.example.scrumpokerapp.viewmodel.SessionViewModelFactory

class AdminSessionFragment(val session: MutableLiveData<Session>) : Fragment(), CustomCardItemListener, CustomStoryItemListener {

    private lateinit var sessionViewModel: AdminSessionViewModel

    companion object{
        fun newInstance(session: MutableLiveData<Session>) : Fragment{
            return AdminSessionFragment(session)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentSessionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_session,
            container,
            false
        )

        sessionViewModel = ViewModelProviders.of(
            this,
            SessionViewModelFactory(ApiController(), true)
        )[AdminSessionViewModel::class.java]

        binding.cardRecycler.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = UserCardAdapter(
                sessionViewModel.cardsOnTable,
                this@AdminSessionFragment,
                "tableCard",
                (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.uid!!
            )
        }

        binding.storyRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SessionStoryAdapter(sessionViewModel.storyListToWork, this@AdminSessionFragment)
        }

        //Carga de controles
        sessionViewModel.getDeckByUserRole(
            ProjectUtils().getRoleAsString(
                (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.role
            )
        )

        //Observer de controles
        sessionViewModel.deckData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Deck List: ","Cards: " + it.toText())
                binding.controlRecycler.apply {
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    adapter = UserCardAdapter(
                        ProjectUtils().convertCardListToUserCardList(
                            it.data,
                            (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.uid!!
                        ),
                        this@AdminSessionFragment,
                        "control",
                        (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.uid!!)
                }
            }
        })

        //Inicia Carga de snapshots//

        //Cargar snapshot de la historia activa
        sessionViewModel.getCurrentStorySessionSanpshot(session.value?.session_id.toString())

        //Cargar snapshot de la sesion activa
        sessionViewModel.getSessionSnapshot(session.value?.session_id.toString())

        //Cargar snapshot de historia actualizada
        sessionViewModel.getUpdatedStorySnapshot(session.value?.session_id.toString())

        //Cargar snapshot de backlog
        sessionViewModel.getBacklogSnapshot(session.value?.session_id.toString())

        //Finaliza Carga de snapshots//

        //Cargar historias a trabajar
        sessionViewModel.getSessionStories(
            session.value?.session_id.toString()
        )

        //Observer de carga de historias a trabajar
        sessionViewModel.sessionStoryList.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.loadSessionStories(it.data)
                binding.storyRecycler.adapter?.notifyDataSetChanged()
            } else {
                //Capturar error de carga de historias a backlog
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        sessionViewModel.sessionSnapshotData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.status == "onBreak"){
                    goToHome()
                } else if (it.status == "finished"){
                    binding.progressBar.visibility = View.VISIBLE
                    sessionViewModel.validateBacklog(session.value!!)
                }
            }
        })

        //Observer de la historia lanzada en snapshot
        sessionViewModel.currentStorySnapshot.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("Current Story Data: ","SNAPSHOT: " + it.transformToJASONtxt())
                sessionViewModel.loadStoryItem(binding.currentStoryLayout, it)
                //Cargar snapshot de la mesa
                sessionViewModel.getPokerTableSnapshot(session.value?.session_id.toString(), it.doc_id!!)
            } else {
                Log.i("Story List: ","WAITING...")
                sessionViewModel.canSend = true
                sessionViewModel.loadStoryStandByListItem(binding.currentStoryLayout)
                sessionViewModel.getSessionStories(session.value?.session_id.toString())
            }
        })

        sessionViewModel.updatedStorySnapshot.observe(viewLifecycleOwner, Observer {
            if (it != null){
                sessionViewModel.cardsOnTable.clear()
                binding.cardRecycler.adapter?.notifyDataSetChanged()

                Log.i("Story List: ","UPDATED!")
                sessionViewModel.unlockLaunchStory = true
                sessionViewModel.loadStoryStandByListItem(binding.currentStoryLayout)
                sessionViewModel.getSessionStories(session.value?.session_id.toString())
            }
        })

        sessionViewModel.isAllStoryList.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("Story List: ","FINISHED: ")
                sessionViewModel.loadEndoOfListItem(binding.currentStoryLayout)
                sessionViewModel.getSessionStories(session.value?.session_id.toString())
            }
        })

        //Observer de la mesa
        sessionViewModel.tableCardListSnapshot.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.loadTableCards(it.data)
            } else {
                sessionViewModel.clearTableCards()
            }

            //Recargar adapter de cartas en mesa
            binding.cardRecycler.adapter?.notifyDataSetChanged()
        })

        sessionViewModel.goToHomeData.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                if (it){
                    goToHome()
                }
            }
        })

        sessionViewModel.showStoryListRecycler.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                if(it){
                    sessionViewModel.unlockLaunchStory = false
                    binding.cardRecycler.visibility = View.GONE
                    binding.storyRecycler.visibility = View.VISIBLE
                } else {
                    binding.cardRecycler.visibility = View.VISIBLE
                    binding.storyRecycler.visibility = View.GONE
                }
            }
        })

        sessionViewModel.backlogSnapshotData.observe(viewLifecycleOwner, Observer {
            if (it != null ){
                //Backlog Creado
                //Cargar snapshot de historias de backlog
                sessionViewModel.backlogID = it.doc_id.toString()
                sessionViewModel.getBacklogStoryListSnapshot(sessionViewModel.backlogID)

                sessionViewModel.getStoriesForBacklog(session.value?.session_id!!)
            } else {
                //Capturar error de backlog no creado
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        sessionViewModel.backlogStoryList.observe(viewLifecycleOwner, Observer {
            if (it != null ){
                //Historias para el backlog
                sessionViewModel.addStoriesToBacklog(sessionViewModel.backlogStoryList.value?.data!!)

                //Actualizar status de proyecto
                sessionViewModel.updateProjectStatus(
                    Project(
                        session.value?.project_name,
                        session.value?.project_id,
                        session.value?.session_id,
                        sessionViewModel.backlogID,
                        sessionViewModel.getProjectStatus()
                    )
                )
            } else {
                //Capturar error de backlog no creado
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        sessionViewModel.projectLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                sessionViewModel.updateAdminStatus(
                    (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()
                )
            }
        })

        sessionViewModel.adminLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                (activity as? MainActivity)?.mainActivityViewModel?.updateUserProfile(it.data.get(0))
                Toast.makeText(context,"Sesion Finalizada!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.INVISIBLE
                goToHome()
            }
        })

        return binding.root
    }

    private fun goToHome() {
        sessionViewModel.goToHomeData.postValue(false)
        (activity as? MainActivity)?.mainActivityViewModel?.showBottomNavigationMenu?.postValue(true)
        (activity as? MainActivity)?.replaceFragment(HomeFragment.newInstance(), "HomeFragment")
    }

    override fun getSelectedCardItem(userCard: UserCard, type: String) {
        userCard.story_id = sessionViewModel.currentStorySnapshot.value?.doc_id
        sessionViewModel.executeAction(
            session.value!!,
            userCard,
            ProjectUtils().isTableCard(type)
        )
    }

    override fun getSelectedItem(story: BacklogStory) {
        TODO("Not yet implemented")
    }

    override fun getSelectedItem(adapterStory: SessionStory) {
        Log.i("Current Story Data: ","ADAPTER ITEM: " + adapterStory.transformToJASONtxt())
        showConfirmDialog(adapterStory)
    }

    fun showConfirmDialog(story: SessionStory) {

        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Lanzar Historia")
        alertDialog.setMessage(
            "Esta seguro de lanzar la siguiente historia?\n" +
            story.transformToDialogAlertText()
        )

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si") { dialog, which ->
            sessionViewModel.launchStory(story, session.value?.session_id!!)
            sessionViewModel.showStoryListRecycler.postValue(false)
            dialog.dismiss()
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, which ->
            dialog.dismiss()
        }

        alertDialog.show()

        val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 10f
        btnPositive.layoutParams = layoutParams
        btnNegative.layoutParams = layoutParams
    }
}