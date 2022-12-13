package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentSpeechAssistantPageBinding
import com.amos.infotaimos.viewmodel.SpeechAssistantViewModel
import java.util.*
import kotlin.concurrent.schedule

class SpeechAssistantPage : ViewBindingFragment<FragmentSpeechAssistantPageBinding>() {
    private val viewModel: SpeechAssistantViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentPttButton.setOnClickListener {
            viewModel.startSpeechAssistant(requireContext())
            binding.fragmentSpeechAnnouncementTextView.visibility = View.VISIBLE
            Timer().schedule(5500) {
                binding.fragmentSpeechAnnouncementTextView.visibility = View.INVISIBLE
            }
        }
        binding.fragmentTttButton.setOnClickListener {
            viewModel.startSpeechAssistant(requireContext())
            binding.fragmentSpeechAnnouncementTextView.visibility = View.VISIBLE
            Timer().schedule(5500) {
                binding.fragmentSpeechAnnouncementTextView.visibility = View.INVISIBLE
            }
        }
    }
}