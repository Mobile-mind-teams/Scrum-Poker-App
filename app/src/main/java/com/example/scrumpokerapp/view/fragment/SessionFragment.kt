package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentSessionBinding
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.viewmodel.SessionViewModel
import com.example.scrumpokerapp.viewmodel.SessionViewModelFactory

class SessionFragment(val session_id: String) : Fragment() {

    private lateinit var sessionViewModel: SessionViewModel

    companion object{
        fun newInstance(session_id: String) : Fragment{
            return SessionFragment(session_id)
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
            SessionViewModelFactory(ApiController())
        )[SessionViewModel::class.java]

        if (ProjectUtils().isProjectOwner(
                (activity as? MainActivity)?.mainActivityViewModel?.userData?.value?.role
        )){
            //Para PO
            //Carga de objeto de sesion
            sessionViewModel.getSessionData(session_id)

            //Observer de carga de sesion
            sessionViewModel.sessionData.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    sessionViewModel.loadSessionObject(it.data.get(0))
                    sessionViewModel.getSessionStories(
                        session_id
                    )
                }
            })

            //Observer de carga de historias a trabajar
            sessionViewModel.sessionStoryList.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    sessionViewModel.loadSessionStories(it.data)
                }
            })

            sessionViewModel.isAllStoryList.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    Log.i("Story List: ","FINISHED: ")
                    sessionViewModel.loadEndoOfListItem(binding.currentStoryLayout)
                    sessionViewModel.getSessionStories(session_id)
                }
            })
        }

        //Para todos
        //Observer de la historia lanzada en snapshot
        sessionViewModel.currentStory.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("Current Story Data: ","SNAPSHOT: " + it.transformToJASONtxt())
                if(it.agreed_status == true){
                    sessionViewModel.clearTable(session_id, it.doc_id.toString())
                    sessionViewModel.getClearPokerTableSnapshot(session_id)
                } else {
                    sessionViewModel.loadStoryItem(binding.currentStoryLayout, it)
                    sessionViewModel.getPokerTableSnapshot(session_id, it.doc_id.toString())
                }
            }
        })

        //Cargar snapshot de la historia activa
        sessionViewModel.getCurrentStorySessionSanpshot(session_id)

        //Cargar snapshot de la sesion activa
        sessionViewModel.getSessionSnapshot(session_id)

        sessionViewModel.sessionSnapshotData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.status == "onBreak"){
                    sessionViewModel.clearTable(session_id, sessionViewModel.currentStory.value?.doc_id!!)
                    goToHome()
                } else if (it.status == "finishAllStories") {

                } else if (it.status == "finished"){

                }
            }
        })

        //Observer de la mesa
        sessionViewModel.tableData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.loadTableCards(it.data)
                //Recargar adapter de cartas en mesa
//                binding.tableCards.setText(it.toText())
            }
        })

        sessionViewModel.clearTableData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if (it.message == "Deleted!" && sessionViewModel.currentStory.value?.agreed_status == false){
                } else if ((it.message == "Deleted!" && sessionViewModel.currentStory.value?.agreed_status == true)){
                    sessionViewModel.launchStory(session_id, sessionViewModel.currentStory.value)
                }
                //Vaciar adapter de cartas en mesa
//                binding.tableCards.setText("Vacio")
            } else {
                Log.i("Poker Table snapshot: ","it: NULL")
            }
        })

        //observer de la accion seleccionada
        sessionViewModel.tableCardSent.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Table Card: ","POST CURRENT USER: " + it.toText())
            }
        })

        //Carga de controles
        sessionViewModel.getDeckByUserRole(
            ProjectUtils().getRoleAsString(
                (activity as? MainActivity)?.mainActivityViewModel?.userData?.value?.role
            )
        )

        //Observer de controles
        sessionViewModel.deckData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                //Cargar adapter de controles
                Log.i("Deck List: ","Cards: " + it.toText())
            }
        })

        //Acciones de controles
        /*binding.actionSetTableCard.setOnClickListener {
            sessionViewModel.setTableCard(
                UserCard(
                    "user_id",
                    2.0,
                    "action",
                    false,
                    sessionViewModel.currentStory.value?.doc_id,
                    "name",
                    ""
                ),
                session_id
            )
        }

        binding.actionLaunchStory.setOnClickListener {
            sessionViewModel.launchStory(
                session_id,
                sessionViewModel.currentStory.value
            )
        }

        binding.actionSetValue.setOnClickListener {
            sessionViewModel.updateStoryValue(session_id, 5.0)
        }

        binding.actionRepeatTurn.setOnClickListener {
            sessionViewModel.repeatTurn(session_id, sessionViewModel.currentStory.value?.doc_id!!)
        }

        binding.actionGoToBreak.setOnClickListener {
            sessionViewModel.goToBreak(sessionViewModel.sessionObject)
        }

        binding.actionSetNote.setOnClickListener {
            sessionViewModel.addStoryNote(session_id,binding.actionSetNote.text.toString())
        }

        binding.actionFlipCards.setOnClickListener {
            sessionViewModel.flipCards(session_id, sessionViewModel.cardsOnTable)
        }*/

        return binding.root
    }

    private fun goToHome() {
        (activity as? MainActivity)?.mainActivityViewModel?.showBottomNavigationMenu?.postValue(true)
        (activity as? MainActivity)?.replaceFragment(HomeFragment.newInstance(), "HomeFragment")
    }
}