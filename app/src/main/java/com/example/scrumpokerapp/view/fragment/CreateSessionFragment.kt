package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.databinding.FragmentCreateSessionBinding

class CreateSessionFragment: Fragment() {

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

        binding.sessionParams.text = savedInstanceState?.get("session_id").toString()
        binding.sessionParams.text = binding.sessionParams.text.toString() + savedInstanceState?.get("project_id").toString()

        return binding.root
    }
}