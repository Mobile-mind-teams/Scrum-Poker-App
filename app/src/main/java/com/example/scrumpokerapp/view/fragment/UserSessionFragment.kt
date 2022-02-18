package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.UserCardAdapter
import com.example.scrumpokerapp.view.listener.CustomCardItemListener
import com.example.scrumpokerapp.viewmodel.SessionViewModel
import com.example.scrumpokerapp.viewmodel.SessionViewModelFactory
import com.example.scrumpokerapp.viewmodel.UserSessionViewModel

class UserSessionFragment(val session: MutableLiveData<Session>) : Fragment(), CustomCardItemListener {

    private lateinit var sessionViewModel: UserSessionViewModel

    companion object{
        fun newInstance(session: MutableLiveData<Session>) : Fragment {
            return UserSessionFragment(session)
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
            SessionViewModelFactory(ApiController(), false)
        )[UserSessionViewModel::class.java]

        binding.cardRecycler.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = UserCardAdapter(
                sessionViewModel.cardsOnTable,
                this@UserSessionFragment,
                "tableCard",
                (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.uid!!
            )
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
                        this@UserSessionFragment,
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

        //Finaliza Carga de snapshots//

        sessionViewModel.sessionSnapshotData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.status == "onBreak"){
                    goToHome()
                } else if (it.status == "finished"){
                    sessionViewModel.updateUserStatus(
                        (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()
                    )
                }
            }
        })

        //Para todos
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
            }
        })

        sessionViewModel.updatedStorySnapshot.observe(viewLifecycleOwner, Observer {
            if (it != null){
                sessionViewModel.cardsOnTable.clear()
                binding.cardRecycler.adapter?.notifyDataSetChanged()

                Log.i("Story List: ","UPDATED!")
                sessionViewModel.loadStoryStandByListItem(binding.currentStoryLayout)
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

        sessionViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                (activity as? MainActivity)?.mainActivityViewModel?.updateUserProfile(it.data.get(0))
                Toast.makeText(context,"Sesion Finalizada!", Toast.LENGTH_SHORT).show()
                goToHome()
            }
        })

        sessionViewModel.userCardInfo.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(context,"Mandaste: " + it.name + " Valor: " + it.value, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Esta no es tu carta", Toast.LENGTH_SHORT).show()
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
        userCard.story_id = sessionViewModel.currentStory.value?.doc_id
        sessionViewModel.executeAction(
            session.value!!,
            userCard,
            ProjectUtils().isTableCard(type),
            (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()
        )
    }
}