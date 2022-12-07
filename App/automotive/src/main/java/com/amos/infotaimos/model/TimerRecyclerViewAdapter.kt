package com.amos.infotaimos.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.databinding.TimerListItemBinding

class TimerRecyclerViewAdapter :
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
        holder.bindValues(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class TimerItemViewHolder(private val itemBinding: TimerListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Function for binding a new timerItem values to view holder
         * @param timerItem TimerItem object with values
         */
        fun bindValues(timerItem: TimerItem) {
            itemBinding.time.text = timerItem.time
            itemBinding.actionIdAndDuration.text = timerItem.actionIdAndDuration
            itemBinding.description.text = timerItem.description

        }
    }
}