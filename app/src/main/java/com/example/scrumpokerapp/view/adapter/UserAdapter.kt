package com.example.scrumpokerapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.view.listener.CustomUserItemListener
import com.google.android.gms.common.util.Hex

class UserAdapter(val list: List<User>, val listener: CustomUserItemListener? = null) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.findViewById<LinearLayout>(R.id.touchablePart).setOnClickListener {
            holder.itemView.setBackgroundColor(android.graphics.Color.parseColor("#758CBB"))
            listener?.getSelectedItemEmail(list[position].email.toString())
        }

        holder.itemView.findViewById<ImageButton>(R.id.remove_addresse).setOnClickListener {
            holder.itemView.setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"))
            listener?.dropSelectedItemEmail(list[position].email.toString())
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.user_name).text = user.user_name
            itemView.findViewById<TextView>(R.id.user_email).text = user.email
        }
    }
}