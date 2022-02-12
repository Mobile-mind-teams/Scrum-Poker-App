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
import com.example.scrumpokerapp.databinding.FragmentCreateBacklogSessionBinding
import com.example.scrumpokerapp.databinding.FragmentCreateSessionBinding
import com.example.scrumpokerapp.model.*
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.UserAdapter
import com.example.scrumpokerapp.view.listener.CustomUserItemListener
import com.example.scrumpokerapp.viewmodel.CreateBacklogSessionViewModel
import com.example.scrumpokerapp.viewmodel.CreateBacklogSessionViewModelFactory

class CreateBacklogSessionFragment(val backlog: Backlog) : Fragment(), CustomUserItemListener {

    private lateinit var createbacklogSessionViewModel: CreateBacklogSessionViewModel

    companion object {
        fun newInstance(backlog: Backlog) : Fragment {
            return  CreateBacklogSessionFragment(backlog)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreateBacklogSessionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_backlog_session,
            container,
            false
        )

        createbacklogSessionViewModel = ViewModelProviders.of(
            this,
            CreateBacklogSessionViewModelFactory(ApiController())
        )[CreateBacklogSessionViewModel::class.java]

        binding.projectName.setText(backlog.project_name)

        loadData()

        createbacklogSessionViewModel.usersListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.usersRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = UserAdapter(it.data,this@CreateBacklogSessionFragment)
                }
            }
        })

        binding.btnCreateSession.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            createbacklogSessionViewModel.createSession(
                Session(
                    createbacklogSessionViewModel.projectItem.name.toString(),
                    createbacklogSessionViewModel.projectItem.project_id.toString(),
                    (activity as? MainActivity)?.mainActivityViewModel?.userData?.value?.uid.toString(),
                    createbacklogSessionViewModel.getSelectedUsersEmailList()
                )
            )

            Toast.makeText(context,"Creando Session...", Toast.LENGTH_SHORT).show()
        }

        //Agregar Snapshot
        /*createSessionViewModel.newSessionMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Toast.makeText(context,"Obteniendo Historias de la Session...", Toast.LENGTH_SHORT).show()

                createSessionViewModel.getProjectStories(
                    createSessionViewModel.projectItem.project_id.toString()
                )
            } else {
                Toast.makeText(context,"Error al crear Session!", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })*/

        /*createbacklogSessionViewModel.projectStoryMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(context,"Enviando Invitaciones...", Toast.LENGTH_SHORT).show()

                createbacklogSessionViewModel.sendEmail(
                    Email(
                        createbacklogSessionViewModel.projectItem.name.toString(),
                        createbacklogSessionViewModel.emailAddresseUserList)
                )
            } else {
                Toast.makeText(context,"Error al crear Session!", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })*/

        createbacklogSessionViewModel.emailMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.isSuccess()){
                    Toast.makeText(context,"Invitaciones Enviadas!", Toast.LENGTH_SHORT).show()
                    Log.i("Email Response: ","RAW: " + 200 + " " + it.toText())
                } else {
                    Toast.makeText(context,"Surgio un error al enviar invitaciones!", Toast.LENGTH_SHORT).show()
                    Log.i("Email Response: ","ERROR: " + 500 + " " + it.errorToText())
                }

                Toast.makeText(context,"Actualizando Estados del Sistema...", Toast.LENGTH_SHORT).show()
                createbacklogSessionViewModel.updateAdminStatus(
                    (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                )
            } else {
                Toast.makeText(context,"Invitaciones No Enviadas...", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })

        createbacklogSessionViewModel.adminUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                (activity as? MainActivity)?.mainActivityViewModel?.userData?.postValue(
                    createbacklogSessionViewModel.updateUserProfile(
                        (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                    )
                )
                createbacklogSessionViewModel.updateUsersStatus()
            } else {
                Toast.makeText(context,"Error al Actualizar Estados!", Toast.LENGTH_SHORT).show()
                goToError(binding.progressBar)
            }
        })

        createbacklogSessionViewModel.usersUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if (it.data.get(0).email.equals(
                        createbacklogSessionViewModel.selectedUsersEmailList.get(
                            createbacklogSessionViewModel.selectedUsersEmailList.lastIndex
                        )
                    )
                ){
                    Log.i("Last User Updated: ","Email: " + it.data.get(0).email.toString())
                    createbacklogSessionViewModel.getNewSessionID(
                        (activity as? MainActivity)?.mainActivityViewModel?.userData?.value
                    )
                } else {
                    Log.i("User Updated: ","Email: " + it.data.get(0).email.toString())
                }
            }
        })

        createbacklogSessionViewModel.newSessionIDMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                Toast.makeText(context,"Agregando Historias a la Session...", Toast.LENGTH_SHORT).show()

                createbacklogSessionViewModel.addStoriesToSession(
                    ProjectUtils().convertProjectStoriesToSessionStories(
                        createbacklogSessionViewModel.projectStoryListMutableLiveData.value?.data!!
                    ),
                    it.data.get(0).session_id.toString()
                )
            }
        })

        createbacklogSessionViewModel.sessionStoryListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                createbacklogSessionViewModel.updateProjectStatus(
                    Project(
                        createbacklogSessionViewModel.newSessionIDMutableLiveData.value?.data?.get(0)?.project_id.toString(),
                        createbacklogSessionViewModel.newSessionIDMutableLiveData.value?.data?.get(0)?.session_id.toString(),
                        "assigned"
                    )
                )
            }
        })

        createbacklogSessionViewModel.projectUpdateMutableLiveData.observe(viewLifecycleOwner, Observer {
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

    override fun getSelectedUserItem(user: User) {
        /*var existInList = false
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
        }*/
    }

    override fun dropSelectedUserItem(user: User) {
        /*try {
            createSessionViewModel.selectedUsers.removeAt(
                createSessionViewModel.selectedUsers.indexOf(user)
            )
        } catch (e: Exception){

        }*/
    }

    fun loadData(){
        createbacklogSessionViewModel.loadAvailableUsersList()
    }
}