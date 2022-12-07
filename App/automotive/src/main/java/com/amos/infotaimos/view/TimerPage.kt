package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentTimerPageBinding
import com.amos.infotaimos.model.TimerRecyclerViewAdapter
import com.amos.infotaimos.viewmodel.TimerPageViewModel

class TimerPage : ViewBindingFragment<FragmentTimerPageBinding>() {
    private val viewModel: TimerPageViewModel by viewModels()

    private lateinit var adapter: TimerRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TimerRecyclerViewAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.events.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
