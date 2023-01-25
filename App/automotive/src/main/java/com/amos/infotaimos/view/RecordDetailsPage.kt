package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentRecordDetailsBinding
import com.amos.infotaimos.model.RecordDetailsRecyclerViewAdapter
import com.amos.infotaimos.viewmodel.RecordDetailsViewModel

class RecordDetailsPage : ViewBindingFragment<FragmentRecordDetailsBinding>() {

    private val viewModel: RecordDetailsViewModel by viewModels()
    private lateinit var adapter: RecordDetailsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecordDetailsRecyclerViewAdapter()
        val id = arguments?.getString("id")
        if (id != null) {
            viewModel.initialise(requireContext(), id)
        }
        binding.detailRecordsRecyclerView.adapter = adapter
        viewModel.events.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.buttonExport.setOnClickListener{
            viewModel.exportTestDrive(requireContext())
        }
    }
}