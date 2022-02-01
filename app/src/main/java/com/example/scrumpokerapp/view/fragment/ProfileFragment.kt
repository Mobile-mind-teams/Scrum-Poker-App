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
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.databinding.FragmentProfileBinding
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.viewmodel.ProfileViewModel
import com.example.scrumpokerapp.viewmodel.ProfileViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        fun newInstance() : Fragment {
            return  ProfileFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        profileViewModel = ViewModelProviders.of(
            this,
            ProfileViewModelFactory(requireActivity().application)
        )[ProfileViewModel::class.java]

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.role_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.etRole.setText(adapter.getItem(UserProfile.role.toString().toInt()).toString())
        }

        binding.etEmail.setText(UserProfile.email)

        binding.etUserName.setText(UserProfile.user_name)

        binding.etStatus.setText(UserProfile.status)

        binding.btnLogOut.setOnClickListener {
            profileViewModel.logOut()
        }

        profileViewModel.sessionStatus.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if(it){
                    (activity as? MainActivity)?.mainActivityViewModel?.showBottomNavigationMenu?.postValue(false)
                    (activity as? MainActivity)?.mainActivityViewModel?.loggedStatus?.postValue(false)
                    (activity as? MainActivity)?.replaceFragment(LogInFragment.newInstance(), "LogInFragment")
                }
            }
        })

        return binding.root

    }
}