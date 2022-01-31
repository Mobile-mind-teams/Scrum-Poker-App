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
import com.example.scrumpokerapp.databinding.FragmentCreateSessionBinding
import com.example.scrumpokerapp.model.Email
import com.example.scrumpokerapp.view.adapter.ProjectAdapter
import com.example.scrumpokerapp.view.adapter.UserAdapter
import com.example.scrumpokerapp.view.listener.CustomItemListener
import com.example.scrumpokerapp.view.listener.CustomUserItemListener
import com.example.scrumpokerapp.viewmodel.CreateSessionViewModel
import com.example.scrumpokerapp.viewmodel.CreateSessionViewModelFactory

class CreateSessionFragment: Fragment(), CustomUserItemListener, CustomItemListener {

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

        createSessionViewModel.loadAvailableUsersList()

        createSessionViewModel.loadUnassignedProjects()

        createSessionViewModel.projectListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.projectsRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ProjectAdapter(it.data,this@CreateSessionFragment)
                }
            }
        })

        createSessionViewModel.userListMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.usersRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = UserAdapter(it.data,this@CreateSessionFragment)
                }
            }
        })

        createSessionViewModel.emailMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(context,"Invitacion Enviada!", Toast.LENGTH_SHORT).show()
                Log.i("Email Response: ","RAW: " + 200 + " " + it.toText())
            } else {
                Toast.makeText(context,"Invitacion No Enviada...", Toast.LENGTH_SHORT).show()
            }

            binding.progressBar.visibility = View.INVISIBLE
        })

        binding.btnCreateSession.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            createSessionViewModel.sendEmail(
                Email(createSessionViewModel.projectName,createSessionViewModel.emailUserListToString())
            )
            Toast.makeText(context,"Enviando Invitacion...", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun getSelectedItemDocId(name: String) {
        createSessionViewModel.projectName = name
    }

    override fun getSelectedItemEmail(email: String) {
        var existInList = false
        if (createSessionViewModel.emailUserList.size > 0) {
            for (currentMail: String in createSessionViewModel.emailUserList) {
                if (email == currentMail) {
                    existInList = true
                }
            }

            if (!existInList){
                createSessionViewModel.emailUserList.add(email)
            }
        } else {
            createSessionViewModel.emailUserList.add(email)
        }
    }

    override fun dropSelectedItemEmail(email: String) {
        try {
            createSessionViewModel.emailUserList.removeAt(
                createSessionViewModel.emailUserList.indexOf(email)
            )
        } catch (e: Exception){

        }
    }
}