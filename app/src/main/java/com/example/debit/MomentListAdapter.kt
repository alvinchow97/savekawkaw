package com.example.debit

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MomentListAdapter internal constructor(context: Context):
        RecyclerView.Adapter<MomentListAdapter.MomentViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var topics = emptyList<Topic>() // Cached copy of user

    inner class MomentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topicTitleTextView: TextView = itemView.findViewById(R.id.textViewTopic)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val itemView = inflater.inflate(R.layout.moment_record, parent, false)

        return MomentViewHolder(itemView)
    }

    override fun getItemCount()=topics.size;

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        val current = topics[position]
        holder.topicTitleTextView.text = current.topictitle

    }

    internal fun setTopic(topics: List<Topic>) {
        this.topics = topics
        notifyDataSetChanged()
    }

}
