package com.example.scrumpokerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.FragmentBacklogBinding
import com.example.scrumpokerapp.model.Backlog
import com.example.scrumpokerapp.model.BacklogStory
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.adapter.BacklogAdapter
import com.example.scrumpokerapp.view.adapter.BacklogStoryAdapter
import com.example.scrumpokerapp.view.listener.CustomBacklogListener
import com.example.scrumpokerapp.view.listener.CustomStoryItemListener
import com.example.scrumpokerapp.viewmodel.BacklogViewModel
import com.example.scrumpokerapp.viewmodel.BacklogViewModelFactory

class BacklogFragment : Fragment(), CustomBacklogListener, CustomStoryItemListener {

    private lateinit var backlogViewModel: BacklogViewModel

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

        backlogViewModel = ViewModelProviders.of(
            this,
            BacklogViewModelFactory(ApiController())
        )[BacklogViewModel::class.java]

        //Carga inicial de fragment
        backlogViewModel.getBacklogList()

        //Cargar Recycler
        backlogViewModel.backlogListData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.storyRecycler.visibility = View.GONE

                binding.backlogRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = BacklogAdapter(it.data,this@BacklogFragment)
                }
            }
        })

        backlogViewModel.selectedItemDocId.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val doc_id = backlogViewModel.selectedItemDocId.value.toString()
                val collection = "backlog-story"
                binding.backlogRecycler.visibility = View.GONE

                backlogViewModel.loadStoryList(collection, doc_id)
            }
        })

        backlogViewModel.backlogStoryListData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.storyRecycler.visibility = View.VISIBLE
                binding.storyRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = BacklogStoryAdapter(it.data, this@BacklogFragment)
                }
            }
        })

        return binding.root

    }

    override fun getSelectedBacklogItem(backlog: Backlog, longClick: Boolean){
        if (longClick && backlog.status == "incomplete"){
            goToCreateSessionFromBacklog(backlog)
        } else {
            backlogViewModel.selectedItemDocId.postValue(backlog.doc_id)
        }
    }

    private fun goToCreateSessionFromBacklog(backlog: Backlog) {
        (activity as? MainActivity)?.replaceFragment(CreateBacklogSessionFragment.newInstance(backlog), "BacklogFragment")
    }

    override fun getSelectedItem(story: BacklogStory) {
        (activity as? MainActivity)?.replaceFragment(BacklogFragment.newInstance(), "BacklogFragment")
    }


}