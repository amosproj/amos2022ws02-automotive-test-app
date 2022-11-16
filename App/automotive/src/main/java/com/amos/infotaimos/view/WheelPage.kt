package com.amos.infotaimos.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.MainActivity
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
            (requireActivity() as? MainActivity)?.displayToast("PLAY / PAUSE")
            Log.d(TAG, "PLAY / PAUSE pressed")
        }
        binding.wheelButtonSkipForward.setOnClickListener {
            viewModel.handleButtonPress(requireContext(), KeyEvent.KEYCODE_MEDIA_NEXT)
            (requireActivity() as? MainActivity)?.displayToast("SKIP FORWARD")
            Log.d(TAG, "SKIP FORWARD pressed")
        }
    }
    companion object {
        const val TAG = "WHEEL_PAGE"
    }
}
