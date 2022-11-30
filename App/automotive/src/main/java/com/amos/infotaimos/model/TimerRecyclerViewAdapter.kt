package com.amos.infotaimos.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.R

class TimerRecyclerViewAdapter(private val context: Context, private val timerItems: List<TimerItem>) : RecyclerView.Adapter<TimerRecyclerViewAdapter.TimerItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerItemViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.timer_list_item, parent, false)
        return TimerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimerItemViewHolder, position: Int) {
        holder.actionId.text = timerItems[position].actionId
        holder.time.text = timerItems[position].time
        holder.duration.text = timerItems[position].duration
        holder.description.text = timerItems[position].description
    }

    override fun getItemCount(): Int {
        return timerItems.size
    }

    class TimerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val time: TextView = itemView.findViewById(R.id.time)
        val duration: TextView = itemView.findViewById(R.id.duration)
        val description: TextView = itemView.findViewById(R.id.description)
        val actionId: TextView = itemView.findViewById(R.id.actionId)
    }
}