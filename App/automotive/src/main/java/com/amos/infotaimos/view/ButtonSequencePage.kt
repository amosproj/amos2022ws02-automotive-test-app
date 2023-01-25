package com.amos.infotaimos.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentButtonSequenceBinding
import com.amos.infotaimos.model.ButtonSequence
import com.amos.infotaimos.model.ButtonSequenceRecyclerViewAdapter
import com.amos.infotaimos.model.ButtonSequenceStoreService
import com.amos.infotaimos.viewmodel.ButtonSequenceViewModel

class ButtonSequencePage : ViewBindingFragment<FragmentButtonSequenceBinding>() {
    private val viewModel: ButtonSequenceViewModel by viewModels()
    private lateinit var sequenceAdapter: ButtonSequenceRecyclerViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButtonSequencePage.context = requireContext()
        sequenceAdapter = ButtonSequenceRecyclerViewAdapter( { sequenceIndex ->
            viewModel.playSequence(requireContext(), sequences[sequenceIndex])
        })
        binding.sequenceList.adapter = sequenceAdapter
        binding.sequenceList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        val itemTouchHelper = ItemTouchHelper(ButtonSequenceRecyclerViewAdapter.SwipeToDeleteCallback(sequenceAdapter, requireContext()))
        itemTouchHelper.attachToRecyclerView(binding.sequenceList)
        sequenceAdapter.submitList(sequences)
    }

    companion object {
        var context: Context? = null
        val sequences: List<ButtonSequence>
            get() = ButtonSequenceStoreService.loadButtonSequences(context!!) ?: listOf()
    }
}

