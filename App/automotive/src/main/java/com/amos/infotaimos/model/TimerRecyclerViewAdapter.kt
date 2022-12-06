package com.amos.infotaimos.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.databinding.TimerListItemBinding

class TimerRecyclerViewAdapter() :
    RecyclerView.Adapter<TimerRecyclerViewAdapter.TimerItemViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<TimerItem>() {
        override fun areItemsTheSame(oldItem: TimerItem, newItem: TimerItem) =
            oldItem.actionId == newItem.actionId && oldItem.start == newItem.start

        override fun areContentsTheSame(oldItem: TimerItem, newItem: TimerItem) = oldItem == newItem
    })

    fun submitList(list: List<TimerItem>) = differ.submitList(list.toList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerItemViewHolder {
        val itemBinding =
            TimerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimerItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TimerItemViewHolder, position: Int) {
        val timerItem = differ.currentList[position]
        holder.bind(timerItem.actionIdAndDuration, timerItem.time, timerItem.description)
    }

    fun TimerItemViewHolder.bind(actionIdAndDuration: String, time: String, description: String) {
        this.actionIdAndDuration.text = actionIdAndDuration
        this.time.text = time
        this.description.text = description
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class TimerItemViewHolder(private val itemBinding: TimerListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val time: TextView = itemBinding.time
        val actionIdAndDuration: TextView = itemBinding.actionIdAndDuration
        val description: TextView = itemBinding.description
    }
}