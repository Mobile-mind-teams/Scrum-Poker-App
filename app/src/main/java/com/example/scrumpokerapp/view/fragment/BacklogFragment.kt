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
import com.example.scrumpokerapp.view.adapter.BacklogAdapter
import com.example.scrumpokerapp.view.listener.CustomItemListener
import com.example.scrumpokerapp.viewmodel.BacklogViewModel
import com.example.scrumpokerapp.viewmodel.BacklogViewModelFactory

class BacklogFragment : Fragment(), CustomItemListener {

    private lateinit var backlogViewModel: BacklogViewModel
    private lateinit var backlogList: ArrayList<Backlog>

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

        backlogList = arrayListOf()

        backlogViewModel = ViewModelProviders.of(
            this,
            BacklogViewModelFactory(ApiController())
        )[BacklogViewModel::class.java]

        backlogViewModel.getBacklogList()

        //Cargar Recycler
        backlogViewModel.backlogListData.observe(viewLifecycleOwner, Observer {
            if (it != null){

                it.data.forEach {
                    var backlogItem = Backlog(it.project_name.toString(), it.project_id.toString(), it.status.toString(), it.doc_id.toString())
                    backlogList.add(backlogItem)
                }

                binding.backlogRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = BacklogAdapter(backlogList,this@BacklogFragment)
                }
            }
        })

        backlogViewModel.selectedItemDocId.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val bundle = Bundle()
                bundle.putString("doc_id",backlogViewModel.selectedItemDocId.toString())
//                binding.root.findNavController().navigate(R.id., bundle)
            }
        })

        return binding.root

    }

    override fun getSelectedItemDocId(doc_id: String){
        backlogViewModel.selectedItemDocId.postValue(doc_id)
    }
}