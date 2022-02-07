package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentCreateSessionBinding
import com.example.scrumpokerapp.model.Email
import com.example.scrumpokerapp.model.Project
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.ProjectAdapter
import com.example.scrumpokerapp.view.adapter.UserAdapter
import com.example.scrumpokerapp.view.listener.CustomProjectItemListener
import com.example.scrumpokerapp.view.listener.CustomUserItemListener
import com.example.scrumpokerapp.viewmodel.CreateSessionViewModel
import com.example.scrumpokerapp.viewmodel.CreateSessionViewModelFactory

class CreateSessionFragment: Fragment(), CustomUserItemListener, CustomProjectItemListener {

    private lateinit var createSessionViewModel: CreateSessionViewModel

    companion object {
        fun newInstance() : Fragment {
            return  CreateSessionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreateSessionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_session,
            container,
            false
        )

        createSessionViewModel = ViewModelProviders.of(
            this,
            CreateSessionViewModelFactory(ApiController())
        )[CreateSessionViewModel::class.java]

        loadData()

        createSessionViewModel.projectListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.projectsRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ProjectAdapter(it.data,this@CreateSessionFragment)
                }
            }
        })

        createSessionViewModel.usersListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.usersRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = UserAdapter(it.data,this@CreateSessionFragment)
                }
            }
        })

        binding.btnCreateSession.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            createSessionViewModel.createSession(
                Session(
                    createSessionViewModel.projectItem.name.toString(),
                    createSessionViewModel.projectItem.project_id.toString(),
                    (activity as? MainActivity)?.mainActivityViewModel?.userData?.value?.uid.toString(),
                    createSessionViewModel.getSelectedUsersEmailList()
                )
            )

            Toast.makeText(context,"Creando Session...", Toast.LENGTH_SHORT).show()
        }

        createSessionViewModel.newSessionMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Toast.makeText(context,"Obteniendo Historias de la Session...", Toast.LENGTH_SHORT).show()

                createSessionViewModel.getProjectStories(
                    createSessionViewModel.projectItem.project_id.toString()
                )
            } else {
                Toast.makeText(context,"Error al crear Session!", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })

        createSessionViewModel.projectStoryListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(context,"Enviando Invitaciones...", Toast.LENGTH_SHORT).show()

                createSessionViewModel.sendEmail(
                    Email(
                        createSessionViewModel.projectItem.name.toString(),
                        createSessionViewModel.emailAddresseUserList)
                )
            } else {
                Toast.makeText(context,"Error al crear Session!", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })

        createSessionViewModel.emailMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.isSuccess()){
                    Toast.makeText(context,"Invitaciones Enviadas!", Toast.LENGTH_SHORT).show()
                    Log.i("Email Response: ","RAW: " + 200 + " " + it.toText())
                } else {
                    Toast.makeText(context,"Surgio un error al enviar invitaciones!", Toast.LENGTH_SHORT).show()
                    Log.i("Email Response: ","ERROR: " + 500 + " " + it.errorToText())
                }

                Toast.makeText(context,"Actualizando Estados del Sistema...", Toast.LENGTH_SHORT).show()
                createSessionViewModel.updateAdminStatus(
                    (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                )
            } else {
                Toast.makeText(context,"Invitaciones No Enviadas...", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })

        createSessionViewModel.adminUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                (activity as? MainActivity)?.mainActivityViewModel?.userData?.postValue(
                    createSessionViewModel.updateUserProfile(
                        (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                    )
                )
                createSessionViewModel.updateUsersStatus()
            } else {
                Toast.makeText(context,"Error al Actualizar Estados!", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })

        createSessionViewModel.usersUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if (it.data.get(0).email.equals(
                        createSessionViewModel.selectedUsersEmailList.get(
                            createSessionViewModel.selectedUsersEmailList.lastIndex
                        )
                    )
                ){
                    Log.i("Last User Updated: ","Email: " + it.data.get(0).email.toString())
                    createSessionViewModel.getNewSessionID(
                        (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                    )
                } else {
                    Log.i("User Updated: ","Email: " + it.data.get(0).email.toString())
                }
            }
        })

        createSessionViewModel.newSessionIDMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                Toast.makeText(context,"Agregando Historias a la Session...", Toast.LENGTH_SHORT).show()

                createSessionViewModel.addStoriesToSession(
                    ProjectUtils().convertProjectStoriesToSessionStories(
                        createSessionViewModel.projectStoryListMutableLiveData.value?.data!!
                    ),
                    it.data.get(0).session_id.toString()
                )
            }
        })

        createSessionViewModel.sessionStoryListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                createSessionViewModel.updateProjectStatus(
                    Project(
                        createSessionViewModel.newSessionIDMutableLiveData.value?.data?.get(0)?.project_id.toString(),
                        createSessionViewModel.newSessionIDMutableLiveData.value?.data?.get(0)?.session_id.toString(),
                        "assigned"
                    )
                )
            }
        })

        createSessionViewModel.projectUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context,"Historias Agregadas a la Sesion!", Toast.LENGTH_SHORT).show()

                Toast.makeText(context,"Estados Actualizados!", Toast.LENGTH_SHORT).show()

                Toast.makeText(context,"Session Creada!", Toast.LENGTH_SHORT).show()

                binding.progressBar.visibility = View.INVISIBLE
                (activity as? MainActivity)?.mainActivityViewModel?.showCreateSessionBottomNavigationMenuItem?.postValue(false)
                (activity as? MainActivity)?.replaceFragment(HomeFragment.newInstance(), "HomeFragment")
            }
        })

        return binding.root
    }

    private fun goToError(progressBar: ProgressBar) {
        progressBar.visibility = View.INVISIBLE
        (activity as? MainActivity)?.replaceFragment(HomeFragment.newInstance(), "HomeFragment")
    }

    override fun getSelectedItem(project: Project) {
        createSessionViewModel.projectItem = project
    }

    override fun getSelectedUserItem(user: User) {
        var existInList = false
        if (createSessionViewModel.selectedUsers.size > 0) {
            for (currentUser: User in createSessionViewModel.selectedUsers) {
                if (user.email == currentUser.email) {
                    existInList = true
                }
            }

            if (!existInList){
                createSessionViewModel.selectedUsers.add(user)
            }
        } else {
            createSessionViewModel.selectedUsers.add(user)
        }
    }

    override fun dropSelectedUserItem(user: User) {
        try {
            createSessionViewModel.selectedUsers.removeAt(
                createSessionViewModel.selectedUsers.indexOf(user)
            )
        } catch (e: Exception){

        }
    }

    fun loadData(){
        createSessionViewModel.loadAvailableUsersList()

        createSessionViewModel.loadUnassignedProjects()
    }
}