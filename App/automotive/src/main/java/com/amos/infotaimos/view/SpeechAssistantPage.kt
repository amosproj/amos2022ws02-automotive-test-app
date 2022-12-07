package com.amos.infotaimos.view

import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentSpeechAssistantPageBinding
import com.amos.infotaimos.viewmodel.SpeechAssistantViewModel

class SpeechAssistantPage : ViewBindingFragment<FragmentSpeechAssistantPageBinding>() {
    private val viewModel: SpeechAssistantViewModel by viewModels()
}