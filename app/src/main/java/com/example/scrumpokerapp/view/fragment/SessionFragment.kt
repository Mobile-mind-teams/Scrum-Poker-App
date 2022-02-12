package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentSessionBinding
import com.example.scrumpokerapp.model.Project
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.UserCardAdapter
import com.example.scrumpokerapp.view.listener.CustomCardItemListener
import com.example.scrumpokerapp.viewmodel.SessionViewModel
import com.example.scrumpokerapp.viewmodel.SessionViewModelFactory

class SessionFragment(val session_id: String) : Fragment(), CustomCardItemListener {

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

        sessionViewModel.loadUserProfileObject(
            (activity as? MainActivity)?.mainActivityViewModel?.userData?.value!!
        )

        //Carga de objeto de sesion
        sessionViewModel.getSessionData(session_id)

        //Observer de carga de sesion
        sessionViewModel.sessionData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                sessionViewModel.loadSessionObject(it.data.get(0))
                if (ProjectUtils().isProjectOwner(sessionViewModel.currentUser.role)){
                    sessionViewModel.getSessionStories(
                        session_id
                    )
                }
            }
        })

        if (ProjectUtils().isProjectOwner(
                sessionViewModel.currentUser.role
        )){
            //Para PO

            //Iniciar MutableLiveData de backlogStoryListData, projectUpdateMutableLiveData, goToHome
            sessionViewModel.backlogStoryListData.postValue(null)

            sessionViewModel.projectUpdateMutableLiveData.postValue(null)

            sessionViewModel.goToHomeData.postValue(false)

            //Cargar snapshot de backlog
            sessionViewModel.getBacklogSnapshot(session_id)

            //Observer de carga de historias a trabajar
            sessionViewModel.sessionStoryList.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    sessionViewModel.loadSessionStories(it.data)
                    if(sessionViewModel.sessionSnapshotData.value?.status == "finished"){
                        sessionViewModel.createBacklog(sessionViewModel.sessionObject)
                    }
                } else {
                    //Capturar error de carga de historias a backlog
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })

            sessionViewModel.isAllStoryList.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    Log.i("Story List: ","FINISHED: ")
                    sessionViewModel.loadEndoOfListItem(binding.currentStoryLayout)
                    sessionViewModel.getSessionStories(session_id)
                }
            })

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
                            session_id,
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
            } else {
                if (ProjectUtils().isProjectOwner(sessionViewModel.currentUser.role)){
                    sessionViewModel.launchStory(session_id, sessionViewModel.currentStory.value)
                }
            }
        })

        //Cargar snapshot de la historia activa
        sessionViewModel.getCurrentStorySessionSanpshot(session_id)

        //Iniciar snapshot de la sesion activa
        sessionViewModel.sessionSnapshotData.postValue(null)

        //Cargar snapshot de la sesion activa
        sessionViewModel.getSessionSnapshot(session_id)

        sessionViewModel.sessionSnapshotData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.status == "onBreak"){
                    goToHome()
                } else if (it.status == "finished" && !ProjectUtils().isProjectOwner(sessionViewModel.currentUser.role)){
                    sessionViewModel.updateUserStatus(
                        sessionViewModel.currentUser
                    )
                }
                else if (it.status == "finished" && ProjectUtils().isProjectOwner(sessionViewModel.currentUser.role)) {
                    binding.progressBar.visibility = View.VISIBLE
                    sessionViewModel.getStoriesForBacklog(session_id)
                }
            }
        })

        //Observer de la mesa
        sessionViewModel.tableData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.loadTableCards(it.data)
                //Recargar adapter de cartas en mesa
                binding.cardRecycler.apply {
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    adapter = UserCardAdapter(it.data, this@SessionFragment, "tableCard", sessionViewModel.currentUser.uid!!)
                }
            }
        })

        sessionViewModel.clearTableData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if ((it.message == "Deleted!" && sessionViewModel.currentStory.value?.agreed_status == true)){
                    sessionViewModel.currentStory.postValue(null)
                }
                //Vaciar adapter de cartas en mesa
                binding.cardRecycler.apply {
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    adapter = UserCardAdapter(it.data, this@SessionFragment, "tableCard", sessionViewModel.currentUser.uid!!)
                }
            }
        })

        //observer de la accion seleccionada
        sessionViewModel.tableCardSent.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.getCurrentCardSentData(session_id)
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
                Log.i("Deck List: ","Cards: " + it.toText())
                binding.controlRecycler.apply {
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    adapter = UserCardAdapter(
                        ProjectUtils().convertCardListToUserCardList(
                            it.data, sessionViewModel.currentUser.uid!!
                        ), this@SessionFragment, "control", sessionViewModel.currentUser.uid!!)
                }
            }
        })

        sessionViewModel.userCardInfo.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Toast.makeText(context,"Mandaste: " + it.name + " Accion: " + it.action, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Esa no es tu carta: ", Toast.LENGTH_SHORT).show()
            }
        })

        sessionViewModel.userUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
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
            userCard,
            ProjectUtils().isProjectOwner(
                sessionViewModel.currentUser.role
            ),
            ProjectUtils().isTableCard(type)
        )
    }
}