package com.example.debit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DebitAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<DebitAdapter.UserViewHolder>() {

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var credits = emptyList<Credit>()
    //private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewType: TextView = itemView.findViewById(R.id.textViewCreditType)
        val textViewAmount: TextView = itemView.findViewById(R.id.textViewCreditAmount)
        val textViewDate : TextView = itemView.findViewById(R.id.textViewCreditDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.credit_record,parent,false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = credits[position]
        holder.textViewType.text = current.creditType
        holder.textViewAmount.text = current.creditAmount
        holder.textViewDate.text = current.creditDate
    }

    internal fun setCredit(credits:List<Credit>){
        this.credits = credits
        notifyDataSetChanged()
    }


    override fun getItemCount() = credits.size
}