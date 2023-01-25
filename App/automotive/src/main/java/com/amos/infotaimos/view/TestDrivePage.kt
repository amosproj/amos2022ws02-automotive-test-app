package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.amos.infotaimos.MainActivity
import com.amos.infotaimos.R
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentTestDriveBinding
import com.amos.infotaimos.model.TestDriveRecyclerViewAdapter
import com.amos.infotaimos.viewmodel.TestDrivePageViewModel
import java.time.LocalDateTime

class TestDrivePage : ViewBindingFragment<FragmentTestDriveBinding>() {
    private val viewModel: TestDrivePageViewModel by viewModels()
    private lateinit var adapter: TestDriveRecyclerViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var startDate = LocalDateTime.now()
        viewModel._tapText.value = resources.getString(R.string.tap_to_start)
        viewModel.tapText.observe(viewLifecycleOwner) { tapText ->
            binding.testDriveStartTile.tileTestDriveStartTapText.text = tapText
        }
        viewModel.loadTestDrive(requireContext())
        binding.testDriveStartTile.root.setOnClickListener {
            if(viewModel._tapText.value == resources.getString(R.string.tap_to_start)){
                if(viewModel.checkPermission(requireContext())) {
                    viewModel._tapText.value = resources.getString(R.string.tap_to_stop)
                    viewModel.setPropertyManager(requireContext())
                    startDate = LocalDateTime.now()
                    viewModel.record(requireContext(), startDate.toString())
                }
                else{
                    (requireActivity() as? MainActivity)?.displayToast("Missing Permission", 100, 2000)
                }
            }
            else if(viewModel._tapText.value == resources.getString(R.string.tap_to_stop)){
                viewModel._tapText.value = resources.getString(R.string.tap_to_start)
                viewModel.unrecord();
                viewModel.saveTestDrive(requireContext(), startDate)
            }
        }
        adapter = TestDriveRecyclerViewAdapter()
        binding.testDriveRecyclerView.adapter = adapter
        viewModel.events.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        val itemTouchHelper = ItemTouchHelper(TestDriveRecyclerViewAdapter.SwipeToDeleteCallback(adapter, requireContext()))
        itemTouchHelper.attachToRecyclerView(binding.testDriveRecyclerView)
    }
}