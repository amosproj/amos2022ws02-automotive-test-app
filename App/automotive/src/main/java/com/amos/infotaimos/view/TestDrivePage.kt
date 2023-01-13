package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
        viewModel._tapText.value = resources.getString(R.string.tap_to_start)
        viewModel.tapText.observe(viewLifecycleOwner) { tapText ->
            binding.testDriveStartTile.tileTestDriveStartTapText.text = tapText
        }

        binding.testDriveStartTile.root.setOnClickListener {
            if(viewModel._tapText.value == resources.getString(R.string.tap_to_start)){
                viewModel._tapText.value = resources.getString(R.string.tap_to_stop)
                viewModel.setPropertyManager(requireContext())
                if(viewModel.checkPermission(requireContext())) {
                    viewModel.record(requireContext(), LocalDateTime.now().toString())
                }
                else{
                    (requireActivity() as? MainActivity)?.displayToast("Missing Permission", 100, 2000)
                }
            }
            else if(viewModel._tapText.value == resources.getString(R.string.tap_to_stop)){
                viewModel._tapText.value = resources.getString(R.string.tap_to_start)
            }
        }
        adapter = TestDriveRecyclerViewAdapter()
        binding.testDriveRecyclerView.adapter = adapter
    }
}