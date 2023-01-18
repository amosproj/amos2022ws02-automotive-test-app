package com.amos.infotaimos.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentButtonSequenceBinding
import com.amos.infotaimos.model.ButtonSequence
import com.amos.infotaimos.model.ButtonSequenceRecyclerViewAdapter
import com.amos.infotaimos.viewmodel.ButtonSequenceViewModel
import java.util.*

class ButtonSequencePage : ViewBindingFragment<FragmentButtonSequenceBinding>() {
    private val viewModel: ButtonSequenceViewModel by viewModels()
    private lateinit var sequenceAdapter: ButtonSequenceRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sequenceAdapter = ButtonSequenceRecyclerViewAdapter { sequenceIndex ->
            viewModel.playSequence(requireContext(), sequences[sequenceIndex])
        }
        binding.sequenceList.adapter = sequenceAdapter
        binding.sequenceList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        sequenceAdapter.submitList(sequences)
    }
    companion object {
        val sequences = listOf(ButtonSequence(UUID.randomUUID(),listOf(KeyEvent.KEYCODE_MEDIA_PLAY, KeyEvent.KEYCODE_MEDIA_NEXT, KeyEvent.KEYCODE_MEDIA_PAUSE)))
    }
}