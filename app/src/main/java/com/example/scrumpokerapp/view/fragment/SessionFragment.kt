package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentSessionBinding
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.viewmodel.SessionViewModel
import com.example.scrumpokerapp.viewmodel.SessionViewModelFactory

class SessionFragment : Fragment() {

    private lateinit var sessionViewModel: SessionViewModel

    companion object{
        fun newInstance() : Fragment{
            return SessionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentSessionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_session,
            container,
            false
        )

        sessionViewModel = ViewModelProviders.of(
            this,
            SessionViewModelFactory(ApiController())
        )[SessionViewModel::class.java]

        sessionViewModel.getDeckByUserRole(
            ProjectUtils().getRoleAsString(
                (activity as? MainActivity)?.mainActivityViewModel?.userData?.value?.role
            )
        )

        sessionViewModel.getSessionSnapshot()

        sessionViewModel.getSessionStories("x4mHnBMSGGVKF9FRNCCY")

        var insert = true
        if (insert){
            sessionViewModel.setTableCard(
                UserCard(
                    (activity as? MainActivity)?.mainActivityViewModel?.userData?.value?.uid,
                    -1.0,
                    "askForPartition",
                    false,
                    "qOhM5TjqxgJ6WsXV6Ove",
                    "infinito"
                ),
                "x4mHnBMSGGVKF9FRNCCY"
            )

            insert = false
        }

        sessionViewModel.deckData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Deck List: ","Cards: " + it.toText())
            }
        })

        sessionViewModel.sessionStoryList.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Session Story List: ","session-story: " + it.toText())
            }
        })

        sessionViewModel.tableCardSent.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Table Card: ","POST: " + it.toText())
            }
        })

        sessionViewModel.sessionData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("Table Card: ","POST: " + it.toItemCard())
            }
        })

        return binding.root
    }
}