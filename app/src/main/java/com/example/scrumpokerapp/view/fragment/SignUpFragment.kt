package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentSignUpBinding
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.viewmodel.SignUpViewModel
import com.example.scrumpokerapp.viewmodel.SignUpViewModelFactory

class SignUpFragment : Fragment() {

    private lateinit var sigUpViewModel: SignUpViewModel

    companion object {
        fun newInstance() : Fragment {
            return  SignUpFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.role_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spRole.adapter = adapter
        }

        sigUpViewModel = ViewModelProviders.of(
            this,
            SignUpViewModelFactory(ApiController(), requireActivity().application)
        )[SignUpViewModel::class.java]

        binding.btnSignUp.setOnClickListener {

            val user = User(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                "",
                binding.spRole.selectedItemPosition,
                binding.etUserName.text.toString(),
                "",
                "available"
            )

            sigUpViewModel.register(user)

            sigUpViewModel.userData.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    sigUpViewModel.postUser(user)
                    binding.root.findNavController().navigate(R.id.action_signUpFragment_to_logInFragment2)
                }
            })
        }

        return binding.root
    }
}