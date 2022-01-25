package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.controller.ApiSessionController
import com.example.scrumpokerapp.databinding.FragmentHomeBinding
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.view.adapter.SessionAdapter
import com.example.scrumpokerapp.viewmodel.HomeViewModel
import com.example.scrumpokerapp.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    companion object {
        fun newInstance() : Fragment {
            return  HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        homeViewModel = ViewModelProviders.of(
            this,
            HomeViewModelFactory(ApiController(), ApiSessionController(),requireActivity().application)
        )[HomeViewModel::class.java]

        binding.fabAddSession.setOnClickListener{
            homeViewModel.logout()
        }

        homeViewModel.userData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                homeViewModel.getUserById(
                    homeViewModel.getLoggedUserUid()
                )
            }
        })

        homeViewModel.userLoggedData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                homeViewModel.getSessionList()

                if (UserProfile.isProductOwner()){
                    //Retirar boton de aniadir sesion para dt y sm
                    binding.fabAddSession.visibility = View.VISIBLE
                }

                //Tras cargar data del usuario, quitar progress bar
                binding.progressBar.visibility = View.GONE
            }
        })

        //Cargar Recycler para Users
        homeViewModel.historySessionListData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                var sessionList: ArrayList<Session> = arrayListOf()
                it.data.forEach {
                    var sessionItem = Session(it.project_name.toString(), it.pid.toString(), it.sid.toString(), it.status.toString())
                    sessionList.add(sessionItem)
                }

                binding.sessionsRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SessionAdapter(sessionList)
                }
            }
        })

        //Cargar Recycler para PO
        homeViewModel.sesionListData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                var sessionList: ArrayList<Session> = arrayListOf()
                it.data.forEach {
                    var sessionItem = Session(it.project_name.toString(), it.project_id.toString(), it.session_id.toString(),it.status.toString())
                    sessionList.add(sessionItem)
                }

                binding.sessionsRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SessionAdapter(sessionList)
                }
            }
        })

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        homeViewModel.logout()
    }
}