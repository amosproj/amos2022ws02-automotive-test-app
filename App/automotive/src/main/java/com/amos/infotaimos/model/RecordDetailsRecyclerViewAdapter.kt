package com.amos.infotaimos.model


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.databinding.RecordDetailsItemBinding

class RecordDetailsRecyclerViewAdapter() :
    RecyclerView.Adapter<RecordDetailsRecyclerViewAdapter.RecordDetailsveItemViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<DetailRecordsItem>() {
        override fun areItemsTheSame(oldItem: DetailRecordsItem, newItem: DetailRecordsItem) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DetailRecordsItem, newItem: DetailRecordsItem) = oldItem == newItem
    })

    fun submitList(list: List<DetailRecordsItem>) = differ.submitList(list.toList())

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
         * @param detailRecordsItem detailRecordsItem object with values
         */
        fun bindValues(detailRecordsItem: DetailRecordsItem) {
            itemBinding.id.text = detailRecordsItem.id
            itemBinding.eventName.text = detailRecordsItem.eventName
            itemBinding.vehiclePropertyId.text = detailRecordsItem.vehiclePropertyId
        }
    }
}