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
    private var mediaBrowserActive = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MediaRecyclerViewAdapter()
        binding.mediaEventRecyclerView.adapter = adapter

        binding.apply {
            toggleMediaBrowseServiceButton.setOnClickListener { toggleMediaBrowser() }
        }


    }

    private fun toggleMediaBrowser() {
        if (mediaBrowserActive) {
            mediaBrowserActive = false
            viewModel.stopMediaBrowser()
            binding.mediabrowserStatusText.text = "Media Browser deactivated"
            binding.mediabrowserActionText.text = "PRESS TO START MEDIA BROWSER"
        } else {
            mediaBrowserActive = true
            viewModel.startMediaBrowser()
            binding.mediabrowserStatusText.text = "Media Browser activated"
            binding.mediabrowserActionText.text = "PRESS TO STOP MEDIA BROWSER"
        }
    }

}
