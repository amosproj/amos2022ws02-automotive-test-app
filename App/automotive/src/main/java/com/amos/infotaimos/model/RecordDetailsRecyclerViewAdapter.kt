package com.amos.infotaimos.model


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.databinding.RecordDetailsItemBinding

class RecordDetailsRecyclerViewAdapter() :
    RecyclerView.Adapter<RecordDetailsRecyclerViewAdapter.RecordDetailsveItemViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<RecordDetailsItem>() {
        override fun areItemsTheSame(oldItem: RecordDetailsItem, newItem: RecordDetailsItem) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecordDetailsItem, newItem: RecordDetailsItem) = oldItem == newItem
    })

    fun submitList(list: List<RecordDetailsItem>) = differ.submitList(list.toList())

    fun currentList() : List<RecordDetailsItem> = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordDetailsveItemViewHolder {
        val itemBinding =
            RecordDetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordDetailsveItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecordDetailsveItemViewHolder, position: Int) {
        holder.bindValues(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class RecordDetailsveItemViewHolder(private val itemBinding: RecordDetailsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Function for binding a new DetailRecordsItem values to view holder
         * @param recordDetailsItem detailRecordsItem object with values
         */
        fun bindValues(recordDetailsItem: RecordDetailsItem) {
            itemBinding.id.text = recordDetailsItem.id
            itemBinding.eventName.text = recordDetailsItem.eventName
            itemBinding.vehiclePropertyId.text = recordDetailsItem.vehiclePropertyId
            itemBinding.value.text = recordDetailsItem.value
            itemBinding.time.text = recordDetailsItem.time
        }
    }
}