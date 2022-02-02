package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentLogInBinding
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.viewmodel.LogInViewModel
import com.example.scrumpokerapp.viewmodel.LogInViewModelFactory

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

        (activity as? MainActivity)?.mainActivityViewModel?.showBottomNavigationMenu?.postValue(false)

        logInViewModel.userData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                logInViewModel.getUserById(
                    logInViewModel.userData.value?.uid.toString()
                )
            }
        })

        logInViewModel.userLoggedData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                (activity as? MainActivity)?.mainActivityViewModel?.userData?.postValue(
                    UserProfile(
                        it.data.get(0).email.toString(),
                        it.data.get(0).password.toString(),
                        it.data.get(0).uid.toString(),
                        it.data.get(0).role.toString().toInt(),
                        it.data.get(0).user_name.toString(),
                        it.data.get(0).doc_id.toString(),
                        it.data.get(0).status.toString()
                    )
                )
                (activity as? MainActivity)?.mainActivityViewModel?.showBottomNavigationMenu?.postValue(true)
                (activity as? MainActivity)?.mainActivityViewModel?.loggedStatus?.postValue(true)
                (activity as? MainActivity)?.replaceFragment(HomeFragment.newInstance(), "HomeFragment")
            }
        })

        binding.tvSignUp.setOnClickListener{
            (activity as? MainActivity)?.replaceFragment(SignUpFragment.newInstance(), "SignUpFragment")
        }

        return binding.root
    }
}