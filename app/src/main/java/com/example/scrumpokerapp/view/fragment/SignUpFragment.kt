package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentSignUpBinding
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var sigUpViewModel: AuthViewModel
    private lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiController = ApiController()
        sigUpViewModel = activity?.run {
            ViewModelProviders.of(this)[AuthViewModel::class.java]
        } ?: throw Exception("Invalid Fragment")
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

        binding.btnSignUp.setOnClickListener {
            sigUpViewModel.register(
                UsersRegisterRequest(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.spRole.selectedItemPosition,
                    "",
                    binding.etUserName.text.toString()
                )
            )

            sigUpViewModel.userData.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    binding.root.findNavController().navigate(R.id.action_signUpFragment_to_logInFragment2)
                }
            })
        }

        return binding.root
    }
}