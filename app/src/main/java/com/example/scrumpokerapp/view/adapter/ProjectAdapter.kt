package com.example.scrumpokerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.model.Project
import com.example.scrumpokerapp.view.listener.CustomItemListener

class ProjectAdapter(val list: List<Project>, val listener: CustomItemListener? = null) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    var lastSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.project_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        if (lastSelected == position){
            holder.itemView.isSelected = true
            holder.itemView.setBackgroundColor(android.graphics.Color.parseColor("#758CBB"))
        } else {
            holder.itemView.isSelected = false
            holder.itemView.setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"))
        }

        holder.itemView.setOnClickListener {
            if (lastSelected >= 0){
                notifyItemChanged(lastSelected)
            }

            lastSelected = position
            notifyItemChanged(lastSelected)

            listener?.getSelectedItemDocId(list[position].project_id.toString())
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(project: Project) {
            itemView.findViewById<TextView>(R.id.project_name).text = project.name
        }
    }
}