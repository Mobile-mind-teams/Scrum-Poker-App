package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.databinding.FragmentBacklogBinding

class BacklogFragment : Fragment() {
    companion object {
        fun newInstance() : Fragment {
            return  BacklogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentBacklogBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_backlog,
            container,
            false
        )

        return binding.root

    }
}