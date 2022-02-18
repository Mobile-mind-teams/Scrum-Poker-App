package com.example.scrumpokerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.model.Project
import com.example.scrumpokerapp.model.SessionStory
import com.example.scrumpokerapp.view.listener.CustomProjectItemListener
import com.example.scrumpokerapp.view.listener.CustomStoryItemListener

class SessionStoryAdapter(val list: List<SessionStory>, val listener: CustomStoryItemListener? = null) : RecyclerView.Adapter<SessionStoryAdapter.ViewHolder>() {

    var lastSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.story_list_item,
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
            holder.itemView.findViewById<TextView>(R.id.story_title).setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
            holder.itemView.findViewById<TextView>(R.id.story_weight).setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
            holder.itemView.findViewById<TextView>(R.id.story_description).setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
            holder.itemView.findViewById<LinearLayout>(R.id.backlogStory).setBackgroundColor(android.graphics.Color.parseColor("#758CBB"))
        } else {
            holder.itemView.isSelected = false
            holder.itemView.findViewById<TextView>(R.id.story_title).setTextColor(android.graphics.Color.parseColor("#000000"))
            holder.itemView.findViewById<TextView>(R.id.story_weight).setTextColor(android.graphics.Color.parseColor("#000000"))
            holder.itemView.findViewById<TextView>(R.id.story_description).setTextColor(android.graphics.Color.parseColor("#000000"))
            holder.itemView.findViewById<LinearLayout>(R.id.backlogStory).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"))
        }

        holder.itemView.setOnClickListener {
            if (lastSelected >= 0){
                notifyItemChanged(lastSelected)
            }

            lastSelected = position
            notifyItemChanged(lastSelected)

            listener?.getSelectedItem(list[position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(sessionStory: SessionStory) {
            itemView.findViewById<TextView>(R.id.story_title).text = sessionStory.title
            itemView.findViewById<TextView>(R.id.story_weight).text = sessionStory.weight.toString()
            itemView.findViewById<TextView>(R.id.story_description).text = sessionStory.description
        }
    }
}