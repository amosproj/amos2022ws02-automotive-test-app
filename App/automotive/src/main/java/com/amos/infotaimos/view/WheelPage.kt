package com.amos.infotaimos.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentWheelPageBinding
import com.amos.infotaimos.viewmodel.WheelPageViewModel

class WheelPage : ViewBindingFragment<FragmentWheelPageBinding>() {
    private val viewModel: WheelPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO button actions
        binding.wheelButtonPlayPause.setOnClickListener {
            viewModel.handleButtonPress(requireContext(), KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
        }
        binding.wheelButtonSkipForward.setOnClickListener {
            viewModel.handleButtonPress(requireContext(), KeyEvent.KEYCODE_MEDIA_NEXT)
        }
    }
}
