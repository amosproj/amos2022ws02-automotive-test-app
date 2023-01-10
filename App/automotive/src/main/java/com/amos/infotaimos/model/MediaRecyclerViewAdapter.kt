package com.amos.infotaimos.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.databinding.MediaEventItemBinding


class MediaRecyclerViewAdapter :
    RecyclerView.Adapter<MediaRecyclerViewAdapter.MediaEventViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<MediaItem>() {
        override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem) =
            oldItem.actionId == newItem.actionId && oldItem.eventTime == newItem.eventTime

        override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem) = oldItem == newItem
    })

    fun submitList(list: List<MediaItem>) = differ.submitList(list.toList())

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaEventViewHolder {
        val itemBinding =
            MediaEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaEventViewHolder(itemBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MediaEventViewHolder, position: Int) {
        holder.bindValues(differ.currentList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class MediaEventViewHolder(private val itemBinding: MediaEventItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Function for binding a new timerItem values to view holder
         * @param timerItem TimerItem object with values
         */
        fun bindValues(mediaItem: MediaItem) {
            itemBinding.actionId.text = mediaItem.actionId
            itemBinding.description.text = mediaItem.description
            itemBinding.eventTime.text = mediaItem.eventTime.toString()
        }
    }
}
