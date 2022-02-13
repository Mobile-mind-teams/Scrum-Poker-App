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
import com.example.scrumpokerapp.model.Project
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.UserCardAdapter
import com.example.scrumpokerapp.view.listener.CustomCardItemListener
import com.example.scrumpokerapp.viewmodel.AdminSessionViewModel
import com.example.scrumpokerapp.viewmodel.SessionViewModelFactory

class AdminSessionFragment(val session: MutableLiveData<Session>) : Fragment(), CustomCardItemListener {

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
        //Cargar snapshot de backlog
        //sessionViewModel.getBacklogSnapshot(session.value?.session_id.toString())

        //Cargar snapshot de la historia activa
        sessionViewModel.getCurrentStorySessionSanpshot(session.value?.session_id.toString())

        //Cargar snapshot de la sesion activa
        sessionViewModel.getSessionSnapshot(session.value?.session_id.toString())
        //Finaliza Carga de snapshots//

        //Cargar historias a trabajar
        sessionViewModel.getSessionStories(
            session.value?.session_id.toString()
        )

        //Observer de carga de historias a trabajar
        sessionViewModel.sessionStoryList.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.loadSessionStories(it.data)
                if(sessionViewModel.sessionSnapshotData.value?.status == "finished"){
                    sessionViewModel.createBacklog(session.value!!)
                }
            } else {
                //Capturar error de carga de historias a backlog
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        sessionViewModel.sessionSnapshotData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.status == "onBreak"){
                    goToHome()
                } else if (it.status == "finished" && !(activity as? MainActivity)?.mainActivityViewModel?.isProjectOwner()!!){
                    sessionViewModel.updateUserStatus(
                        (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()!!
                    )
                }
                else if (it.status == "finished" && (activity as? MainActivity)?.mainActivityViewModel?.isProjectOwner()!!){
                    binding.progressBar.visibility = View.VISIBLE
                    sessionViewModel.getStoriesForBacklog(session.value?.session_id.toString())
                }
            }
        })

        //Observer de la historia lanzada en snapshot
        sessionViewModel.currentStorySnapshot.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("Current Story Data: ","SNAPSHOT: " + it.transformToJASONtxt())
                if(it.agreed_status == true){
                    sessionViewModel.clearTable(session.value?.session_id.toString(), it.doc_id.toString())
                    sessionViewModel.getClearPokerTableSnapshot(session.value?.session_id.toString())
                } else {
                    sessionViewModel.loadStoryItem(binding.currentStoryLayout, it)
//                    sessionViewModel.getPokerTableSnapshot(session.value?.session_id.toString(), it.doc_id.toString())
                }
            }
        })

        sessionViewModel.isAllStoryList.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("Story List: ","FINISHED: ")
                sessionViewModel.loadEndoOfListItem(binding.currentStoryLayout)
                sessionViewModel.getSessionStories(session.value?.session_id.toString())
            }
        })

        //observer de la accion seleccionada
        sessionViewModel.tableCardSent.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.getCurrentCardSentData(
                    session.value?.session_id.toString(),
                    (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.uid!!
                )
            }
        })

        /*if (ProjectUtils().isProjectOwner(
                sessionViewModel.currentUser.role
        )){
            //Para PO

            //Iniciar MutableLiveData de backlogStoryListData, projectUpdateMutableLiveData, goToHome
            sessionViewModel.backlogStoryListData.postValue(null)

            sessionViewModel.projectUpdateMutableLiveData.postValue(null)

            sessionViewModel.goToHomeData.postValue(false)

            sessionViewModel.backlogSnapshotData.observe(viewLifecycleOwner, Observer {
                if (it != null ){
                    //Backlog Creado

                    //Agregar Historias
                    sessionViewModel.addStoriesToBacklog(
                        sessionViewModel.storyListToWork
                    )
                } else {
                    //Capturar error de backlog no creado
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })

            sessionViewModel.backlogStoryListData.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    //Lista de historias Creada, actualizar proyecto
                    sessionViewModel.updateProjectStatus(
                        Project(
                            sessionViewModel.sessionObject.project_name,
                            sessionViewModel.sessionObject.project_id,
                            session.value?.session_id.toString(),
                            sessionViewModel.backlogSnapshotData.value?.doc_id,
                            sessionViewModel.getProjectStatus()
                        )
                    )
                } else {
                    //Capturar error de lista de backlog no creada
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })

            sessionViewModel.projectUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    sessionViewModel.updateAdminStatus(sessionViewModel.currentUser)
                } else {
                    //Capturar error de actualizacion de proyecto
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })

            sessionViewModel.adminUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    (activity as? MainActivity)?.mainActivityViewModel?.userData?.postValue(
                        sessionViewModel.updateUserProfile(
                            (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                        )
                    )

                    Toast.makeText(context,"Historias Agregadas a Backlog!", Toast.LENGTH_SHORT).show()

                    Toast.makeText(context,"Backlog Creado!", Toast.LENGTH_SHORT).show()

                    binding.progressBar.visibility = View.INVISIBLE
                    goToHome()
                } else {
                    //Capturar error de actualizacion de proyecto
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })

            sessionViewModel.goToHomeData.observe(viewLifecycleOwner, Observer {
                if(it != null) {
                    if (it){
                        goToHome()
                    }
                }
            })
        }*/

        //Para todos




        /*//Iniciar snapshot de la sesion activa
        sessionViewModel.sessionSnapshotData.postValue(null)

        //Cargar snapshot de la sesion activa
        sessionViewModel.getSessionSnapshot(session.value?.session_id.toString())*/



        //Observer de la mesa
        /*sessionViewModel.tableData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.loadTableCards(it.data)
                //Recargar adapter de cartas en mesa
                binding.cardRecycler.apply {
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    adapter = UserCardAdapter(it.data, this@AdminSessionFragment, "tableCard", sessionViewModel.currentUser.uid!!)
                }
            }
        })*/

        /*sessionViewModel.clearTableData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if ((it.message == "Deleted!" && sessionViewModel.currentStorySnapshot.value?.agreed_status == true)){
                    sessionViewModel.currentStorySnapshot.postValue(null)
                }
                //Vaciar adapter de cartas en mesa
                binding.cardRecycler.apply {
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    adapter = UserCardAdapter(it.data, this@AdminSessionFragment, "tableCard", sessionViewModel.currentUser.uid!!)
                }
            }
        })*/

        /*sessionViewModel.userUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                (activity as? MainActivity)?.mainActivityViewModel?.userData?.postValue(
                    sessionViewModel.updateUserProfile(
                        (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                    )
                )
                goToHome()
            } else {
                //Capturar error de actualizacion de proyecto
                binding.progressBar.visibility = View.INVISIBLE
            }
        })*/

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
}