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

class SessionFragment(val session_id: String) : Fragment() {

    private lateinit var sessionViewModel: SessionViewModel

    companion object{
        fun newInstance(session_id: String) : Fragment{
            return SessionFragment(session_id)
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

        binding.actionTest.setOnClickListener {
            sessionViewModel.launchStory(
                session_id
            )
        }

        binding.actionSetValue.setOnClickListener {
            sessionViewModel.updateStoryValue(session_id, 1.0)
        }

        sessionViewModel.getSessionData(session_id)

        sessionViewModel.getCurrentStorySessionSanpshot(session_id)

        sessionViewModel.deckData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Deck List: ","Cards: " + it.toText())
            }
        })

        sessionViewModel.sessionStoryList.observe(viewLifecycleOwner, Observer {
            if(it != null){
                sessionViewModel.loadSessionStories(it.data)
            }
        })

        sessionViewModel.tableCardSent.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Table Card: ","POST: " + it.toText())
            }
        })

        sessionViewModel.sessionData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                sessionViewModel.loadSessionObject(it.data.get(0))
                sessionViewModel.getSessionStories(
                    sessionViewModel.sessionObject.session_id.toString()
                )
            }
        })

        sessionViewModel.updateCurrentStoryData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.i("Current Story Data: ","UPDATE: " + it.toText())
            }
        })

        sessionViewModel.currentStory.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("Current Story Data: ","GET: " + it.transformToJASONtxt())
            }
        })

        sessionViewModel.isAllStoryList.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it){
                    Log.i("Nothing To Show: ","GET STORY LIST " )
                }
            }
        })

        return binding.root
    }
}