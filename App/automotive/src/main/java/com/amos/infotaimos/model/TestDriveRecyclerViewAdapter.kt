package com.amos.infotaimos.model


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.R
import com.amos.infotaimos.databinding.TestDriveItemBinding

class TestDriveRecyclerViewAdapter() :
    RecyclerView.Adapter<TestDriveRecyclerViewAdapter.TestDriveItemViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<TestDriveItem>() {
        override fun areItemsTheSame(oldItem: TestDriveItem, newItem: TestDriveItem) =
            oldItem.actionId == newItem.actionId && oldItem.startTime == newItem.startTime

        override fun areContentsTheSame(oldItem: TestDriveItem, newItem: TestDriveItem) = oldItem == newItem
    })

    fun submitList(list: List<TestDriveItem>) = differ.submitList(list.toList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestDriveItemViewHolder {
        val itemBinding =
            TestDriveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestDriveItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TestDriveItemViewHolder, position: Int) {
        holder.bindValues(differ.currentList[position])
        holder.itemView.setOnClickListener{ view ->
            val id = bundleOf("id" to (differ.currentList[position].startTime).toString() )
            view.findNavController().navigate(R.id.action_testDrivePage_to_recordDetails, id)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class TestDriveItemViewHolder(private val itemBinding: TestDriveItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Function for binding a new testDriveItem values to view holder
         * @param testDriveItem TestDriveItem object with values
         */
        fun bindValues(testDriveItem: TestDriveItem) {
            itemBinding.actionId.text = testDriveItem.actionId
            itemBinding.startDate.text = "Recording from: " + testDriveItem.startDate.toString()
            itemBinding.startTime.text = testDriveItem.time
        }
    }
}