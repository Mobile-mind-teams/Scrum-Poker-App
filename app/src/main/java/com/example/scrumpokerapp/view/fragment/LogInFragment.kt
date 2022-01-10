package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.databinding.FragmentLogInBinding
import com.example.scrumpokerapp.viewmodel.AuthViewModel

class LogInFragment : Fragment() {

    private lateinit var logInViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInViewModel = activity?.run {
            ViewModelProviders.of(this)[AuthViewModel::class.java]
        } ?: throw Exception("Invalid Fragment")
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

        binding.btnLogIn.setOnClickListener {
            logInViewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )

            logInViewModel.userData.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    binding.root.findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                }
            })
        }

        binding.tvSignUp.setOnClickListener{
            binding.root.findNavController().navigate(R.id.action_logInFragment_to_signUpFragment2)
        }

        return binding.root
    }
}