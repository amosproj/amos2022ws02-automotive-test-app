package com.amos.infotaimos.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.MainActivity
import com.amos.infotaimos.R
import com.amos.infotaimos.databinding.SequenceListItemBinding
import com.amos.infotaimos.view.ButtonSequencePage
import com.google.android.material.snackbar.Snackbar

class ButtonSequenceRecyclerViewAdapter(private val itemTapListener: (Int) -> Unit) :
    RecyclerView.Adapter<ButtonSequenceRecyclerViewAdapter.ButtonSequenceViewHolder>() {


    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ButtonSequence>() {
        override fun areItemsTheSame(oldItem: ButtonSequence, newItem: ButtonSequence) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ButtonSequence, newItem: ButtonSequence) =
            oldItem == newItem
    })

    fun submitList(list: List<ButtonSequence>) = differ.submitList(list.toMutableList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonSequenceViewHolder {
        val holder = ButtonSequenceViewHolder(
            SequenceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
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
            itemBinding.sequenceListItemText.text =
                sequenceItem.sequence.joinToString(" - ") { KeyEventControl.nameForKeyCode(it) }
        }
    }

    class SwipeToDeleteCallback(val adapter: ButtonSequenceRecyclerViewAdapter, var context: Context) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = adapter.differ.currentList[position]
            val positionInStorage = ButtonSequencePage.sequences.indexOf(item)

            ButtonSequenceStoreService.deleteButtonSequence(context, item)
            adapter.submitList(ButtonSequenceStoreService.loadButtonSequences(context) ?: listOf())

            val activity = context as MainActivity
            val snackbar = Snackbar.make(activity.findViewById(R.id.main_activity_layout), "Sequence was removed from the list.", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO") {
                ButtonSequenceStoreService.saveButtonSequence(context, item, positionInStorage)
                adapter.submitList(ButtonSequenceStoreService.loadButtonSequences(context) ?: listOf())
            }
            snackbar.show()
        }
    }

}
