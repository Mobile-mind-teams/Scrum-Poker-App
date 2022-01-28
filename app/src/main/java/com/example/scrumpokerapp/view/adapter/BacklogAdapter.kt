package com.example.scrumpokerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.model.Backlog
import com.example.scrumpokerapp.view.listener.CustomItemListener

class BacklogAdapter(val backlogList: ArrayList<Backlog>, val listener: CustomItemListener? = null) : RecyclerView.Adapter<BacklogAdapter.BacklogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BacklogViewHolder {
        return BacklogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.backlog_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = backlogList.size

    override fun onBindViewHolder(holder: BacklogViewHolder, position: Int) {
        holder.bind(backlogList[position])

        holder.itemView.setOnClickListener {
            listener?.getSelectedItemDocId(backlogList[position].doc_id.toString())
        }
    }

    inner class BacklogViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(backlog: Backlog) {
            itemView.findViewById<TextView>(R.id.project_name).text = backlog.project_name
            itemView.findViewById<TextView>(R.id.project_id).text = backlog.project_id
            itemView.findViewById<TextView>(R.id.status).text = backlog.status
        }
    }
}