package com.example.scrumpokerapp.view.fragment

import android.content.Intent
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
import androidx.navigation.findNavController
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentLogInBinding
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.viewmodel.*

class LogInFragment : Fragment() {

    private lateinit var logInViewModel: LogInViewModel

    companion object {
        fun newInstance() : Fragment {
            return  LogInFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentLogInBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_log_in,
            container,
            false
        )

        logInViewModel = ViewModelProviders.of(
            this,
            LogInViewModelFactory(requireActivity().application, ApiController())
        )[LogInViewModel::class.java]

        binding.btnLogIn.setOnClickListener {
            logInViewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )

        }

        logInViewModel.userData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                logInViewModel.getUserById(
                    logInViewModel.userData.value?.uid.toString()
                )
            }
        })

        logInViewModel.userLoggedData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val bundle : Bundle = Bundle()
                bundle.putBoolean("role", UserProfile.isProductOwner())
                binding.root.findNavController().navigate(R.id.action_logInFragment_to_mainActivity, bundle)
            }
        })

        binding.tvSignUp.setOnClickListener{
            binding.root.findNavController().navigate(R.id.action_logInFragment_to_signUpFragment2)
        }

        return binding.root
    }
}