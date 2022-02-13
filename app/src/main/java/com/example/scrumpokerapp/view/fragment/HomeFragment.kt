package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentHomeBinding
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.SessionAdapter
import com.example.scrumpokerapp.view.listener.CustomSessionItemListener
import com.example.scrumpokerapp.viewmodel.HomeViewModel
import com.example.scrumpokerapp.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment(), CustomSessionItemListener {

    private lateinit var homeViewModel: HomeViewModel

    companion object {
        fun newInstance() : Fragment {
            return  HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        homeViewModel = ViewModelProviders.of(
            this,
            HomeViewModelFactory(ApiController(), requireActivity().application)
        )[HomeViewModel::class.java]

        homeViewModel.getSessionList(
            (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()!!
        )

        if ((activity as? MainActivity)?.mainActivityViewModel?.isProjectOwner()!!){
            homeViewModel.loadAdminSessionSnapshot(
                (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.uid.toString()
            )
        } else {
            homeViewModel.loadUserSessionSnapshot(
                (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()?.email.toString()
            )
        }

        homeViewModel.sesionListData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                var sessionList: ArrayList<Session> = arrayListOf()
                it.data.forEach {
                    var sessionItem = Session(
                        it.project_name.toString(),
                        it.project_id.toString(),
                        it.session_id.toString(),
                        it.status.toString(),
                        it.teamList
                    )
                    sessionList.add(sessionItem)
                }

                binding.sessionsRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SessionAdapter(sessionList, this@HomeFragment)
                }
            }
        })

        homeViewModel.sessionStatusData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if (it){
                    goToSession(
                        (activity as? MainActivity)?.mainActivityViewModel?.isProjectOwner()!!
                    )
                }
            }
        })

        homeViewModel.sessionSnapshotData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                homeViewModel.getSessionList(
                    (activity as? MainActivity)?.mainActivityViewModel?.getUserProfile()
                )
                Toast.makeText(context,"Listado Actualizado!", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    override fun getSelectedItem(session: Session) {
        homeViewModel.currentSession.postValue(session)
        homeViewModel.processActionByUserRole(
            session,
            (activity as? MainActivity)?.mainActivityViewModel?.isProjectOwner()!!
        )
    }

    fun goToSession(isAdmin: Boolean){
        (activity as? MainActivity)?.mainActivityViewModel?.showBottomNavigationMenu?.postValue(false)
        if (isAdmin){
            (activity as? MainActivity)?.replaceFragment(AdminSessionFragment.newInstance(homeViewModel.currentSession), "AdminSessionFragment")
        } else {
            (activity as? MainActivity)?.replaceFragment(UserSessionFragment.newInstance(homeViewModel.currentSession), "UserSessionFragment")
        }
    }
}