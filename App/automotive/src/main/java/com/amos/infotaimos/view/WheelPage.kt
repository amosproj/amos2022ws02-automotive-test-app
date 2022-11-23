package com.amos.infotaimos.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.MainActivity
import com.amos.infotaimos.R
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentWheelPageBinding
import com.amos.infotaimos.viewmodel.WheelPageViewModel

class WheelPage : ViewBindingFragment<FragmentWheelPageBinding>() {
    private val viewModel: WheelPageViewModel by viewModels()
    private var description: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO button actions
        binding.wheelButtonPlayPause.setOnClickListener {
            if (!description) {
                viewModel.handleButtonPress(requireContext(), KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
            } else {
                (requireActivity() as? MainActivity)?.displayToast(resources.getString(R.string.play_pause_button_description), 100, 20000)
            }
            Log.d(TAG, "PLAY / PAUSE pressed")
        }
        binding.wheelButtonSkipForward.setOnClickListener {
            if (!description) {
                viewModel.handleButtonPress(requireContext(), KeyEvent.KEYCODE_MEDIA_NEXT)
            } else {
                (requireActivity() as? MainActivity)?.displayToast(resources.getString(R.string.skip_forward_button_description), 100, 20000)
            }
            Log.d(TAG, "SKIP FORWARD pressed")
        }
        binding.wheelButtonVoiceControl.setOnClickListener {
            if (!description) {
                viewModel.handleButtonPress(requireContext(), KeyEvent.KEYCODE_VOICE_ASSIST)
            } else {
                (requireActivity() as? MainActivity)?.displayToast(resources.getString(R.string.voice_control_button_description), 100, 20000)
            }
            Log.d(TAG, "VOICE CONTROL pressed")
        }
        binding.wheelButtonPhone.setOnClickListener {
            if (!description) {
                // TODO phone button action
            } else {
                (requireActivity() as? MainActivity)?.displayToast(resources.getString(R.string.phone_button_description), 100, 20000)
            }
            Log.d(TAG, "PHONE pressed")
        }
        binding.toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            description = isChecked
            Log.d(TAG, "ToggleButton pressed")
        }
    }
    companion object {
        const val TAG = "WHEEL_PAGE"
    }
}
