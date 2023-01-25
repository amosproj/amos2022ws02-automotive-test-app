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
            viewModel.startPTT(requireContext(),getDelay())
        }

        binding.fragmentTttButton.setOnClickListener {
            viewModel.startTTT(requireContext(), getDelay())
        }

        viewModel.active.observe(viewLifecycleOwner){
            if(it){
                binding.fragmentSpeechAnnouncementTextView.visibility = View.VISIBLE
            }
            else{
                Timer().schedule(5500) {
                    binding.fragmentSpeechAnnouncementTextView.visibility = View.INVISIBLE
                }
            }
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