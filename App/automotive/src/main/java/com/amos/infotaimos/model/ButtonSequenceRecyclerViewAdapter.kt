package com.amos.infotaimos.model

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.databinding.SequenceListItemBinding

class ButtonSequenceRecyclerViewAdapter(val itemTapListener: (Int) -> Unit) :
    RecyclerView.Adapter<ButtonSequenceRecyclerViewAdapter.ButtonSequenceViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ButtonSequence>() {
        override fun areItemsTheSame(oldItem: ButtonSequence, newItem: ButtonSequence) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ButtonSequence, newItem: ButtonSequence) = oldItem == newItem
    })

    fun submitList(list: List<ButtonSequence>) = differ.submitList(list.toList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonSequenceViewHolder {
        val holder = ButtonSequenceViewHolder(
            SequenceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        holder.onClick(itemTapListener)
        return holder
    }

    override fun onBindViewHolder(holder: ButtonSequenceViewHolder, position: Int) {
        holder.bindValues(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class ButtonSequenceViewHolder(private val itemBinding: SequenceListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Function for binding a new sequence to view holder
         * @param sequenceItem Sequence with list of buttons
         */
        fun bindValues(sequenceItem: ButtonSequence) {
            itemBinding.sequenceListItemText.text = sequenceItem.sequence.joinToString(" - ") { KeyEventControl.nameForKeyCode(it) }
        }
    }
}