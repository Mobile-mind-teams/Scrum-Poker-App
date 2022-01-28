package com.example.scrumpokerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.model.BacklogStory

class BacklogStoryAdapter(val list: List<BacklogStory>) : RecyclerView.Adapter<BacklogStoryAdapter.ViewHolder>() {

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
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(story: BacklogStory) {
            itemView.findViewById<TextView>(R.id.story_title).text = story.title
            itemView.findViewById<TextView>(R.id.story_weight).text = story.weight
            itemView.findViewById<TextView>(R.id.story_description).text = story.description
        }
    }
}