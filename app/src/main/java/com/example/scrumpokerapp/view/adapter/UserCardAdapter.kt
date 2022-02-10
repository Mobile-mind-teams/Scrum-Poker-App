package com.example.scrumpokerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.listener.CustomCardItemListener

class UserCardAdapter(
    val list: List<UserCard>,
    val listener: CustomCardItemListener? = null,
    val type : String,
    val currentUserId : String
)
    : RecyclerView.Adapter<UserCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.scrum_session_card_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (ProjectUtils().isTableCard(type)){
            holder.bindTableCard(list[position])
        } else {
            holder.bindControlCard(list[position])
        }

        holder.itemView.setOnClickListener {
            listener?.getSelectedCardItem(list[position], type)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindTableCard(userCard: UserCard) {
            //Asignar Valor
            if (userCard.visibility!!) {
                itemView.findViewById<TextView>(R.id.tvName).text = userCard.name
            } else {
                itemView.findViewById<TextView>(R.id.tvName).text = "XXX"
            }

            //Asignar Formato
            if (userCard.user_id == currentUserId) {
                itemView.findViewById<CardView>(R.id.userBackground).setCardBackgroundColor(android.graphics.Color.parseColor("#5F7298"))
                itemView.findViewById<TextView>(R.id.tvName).setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
            } else {
                itemView.findViewById<CardView>(R.id.userBackground).setCardBackgroundColor(android.graphics.Color.parseColor("#000000"))
                itemView.findViewById<TextView>(R.id.tvName).setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
            }
        }

        fun bindControlCard(userCard: UserCard) {
            itemView.findViewById<TextView>(R.id.tvName).text = userCard.name
            itemView.findViewById<CardView>(R.id.userBackground).setCardBackgroundColor(android.graphics.Color.parseColor("#5F7298"))
            itemView.findViewById<TextView>(R.id.tvName).setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
        }
    }
}