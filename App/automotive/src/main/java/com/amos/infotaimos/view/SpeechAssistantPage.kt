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

        binding.fragmentPttButton.setOnClickListener{
            viewModel.startPTT(requireContext())
        }
    }
}