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
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.controller.ApiSessionController
import com.example.scrumpokerapp.databinding.FragmentHomeBinding
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

        binding.tvUserLogged.setOnClickListener{
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
            }
        })

        homeViewModel.historySessionListData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                var txt = ""
                it.data.forEach {
                    txt += it.toItemCard() + "\n"
                }

                binding.tvUserLogged.text = txt
            }
        })

        homeViewModel.sesionListData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                var txt = ""
                it.data.forEach {
                    txt += it.toItemCard() + "\n"
                }

                binding.tvUserLogged.text = txt
            }
        })

        homeViewModel.logOutStatus.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.root.findNavController().navigate(R.id.action_homeFragment_to_logInFragment)
            }
        })

        return binding.root
    }
}