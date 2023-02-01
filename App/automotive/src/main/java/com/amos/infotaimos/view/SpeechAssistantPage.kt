package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentSpeechAssistantPageBinding
import com.amos.infotaimos.viewmodel.SpeechAssistantViewModel

class SpeechAssistantPage : ViewBindingFragment<FragmentSpeechAssistantPageBinding>() {
    private val viewModel: SpeechAssistantViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentPttButton.setOnClickListener {
            viewModel.startPTT(requireContext(),getDelay())
            view.postDelayed( {
                binding.fragmentSpeechAnnouncementTextView.visibility = View.VISIBLE
                view.postDelayed( {
                    binding.fragmentSpeechAnnouncementTextView.visibility = View.INVISIBLE
                }, 5500)
            }, getDelay())
        }

        binding.fragmentTttButton.setOnClickListener {
            viewModel.startTTT(requireContext(), getDelay())
            view.postDelayed( {
                binding.fragmentSpeechAnnouncementTextView.visibility = View.VISIBLE
                view.postDelayed( {
                    binding.fragmentSpeechAnnouncementTextView.visibility = View.INVISIBLE
                }, 5500)
            }, getDelay())
        }
    }



    private fun getDelay(): Long {
        val delayString = binding.delaySpinnerSpeechAssistantPage.selectedItem as String
        var delay = 0L
        if (delayString == "30s")
            delay = 30000L
        else if (delayString == "1min")
            delay = 60000L
        return delay
    }
}