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
import com.example.scrumpokerapp.view.adapter.BacklogAdapter
import com.example.scrumpokerapp.view.adapter.BacklogStoryAdapter
import com.example.scrumpokerapp.view.listener.CustomItemListener
import com.example.scrumpokerapp.viewmodel.BacklogViewModel
import com.example.scrumpokerapp.viewmodel.BacklogViewModelFactory

class BacklogFragment : Fragment(), CustomItemListener {

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
                    adapter = BacklogStoryAdapter(it.data)
                }
            }
        })

        return binding.root

    }

    override fun getSelectedItemDocId(doc_id: String){
        backlogViewModel.selectedItemDocId.postValue(doc_id)
    }
}