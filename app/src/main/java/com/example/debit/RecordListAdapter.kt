package com.example.debit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<RecordListAdapter.UserViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var record = emptyList<Record>() // Cached copy of record

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val crTextView : TextView = itemView.findViewById(R.id.textViewCr)
        val monthTextView : TextView = itemView.findViewById(R.id.textviewMonth)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_record, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = record[position]
        holder.monthTextView.text = current.month
        holder.crTextView.text = current.amount.toString()
    }

    internal fun setRecords(records: List<Record>) {
        this.record = records
        notifyDataSetChanged()
    }

    override fun getItemCount() = record.size
}