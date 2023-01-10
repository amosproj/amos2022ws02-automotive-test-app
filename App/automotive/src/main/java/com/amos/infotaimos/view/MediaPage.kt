package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentMediaPageBinding
import com.amos.infotaimos.model.MediaRecyclerViewAdapter
import com.amos.infotaimos.viewmodel.MediaPageViewModel

class MediaPage : ViewBindingFragment<FragmentMediaPageBinding>() {
    private val viewModel: MediaPageViewModel by viewModels()
    private lateinit var adapter: MediaRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MediaRecyclerViewAdapter()
        binding.mediaEventRecyclerView.adapter = adapter
    }
}
