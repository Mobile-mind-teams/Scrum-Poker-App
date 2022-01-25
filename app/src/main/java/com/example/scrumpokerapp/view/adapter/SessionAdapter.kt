package com.example.scrumpokerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.model.Session

class SessionAdapter(val sessionList: ArrayList<Session>) : RecyclerView.Adapter<SessionAdapter.PokerSessionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokerSessionViewHolder {
        return PokerSessionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.session_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = sessionList.size

    override fun onBindViewHolder(holder: PokerSessionViewHolder, position: Int) {
        holder.bind(sessionList[position])
    }

    inner class PokerSessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(session: Session) {
            itemView.findViewById<TextView>(R.id.project_name).text = session.project_name
            itemView.findViewById<TextView>(R.id.project_id).text = session.project_id
            itemView.findViewById<TextView>(R.id.session_id).text = session.status
        }
    }
}