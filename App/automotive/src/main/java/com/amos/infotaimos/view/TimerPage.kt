package com.amos.infotaimos.view

import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentMediaPageBinding
import com.amos.infotaimos.viewmodel.TimerPageViewModel

class TimerPage : ViewBindingFragment<FragmentMediaPageBinding>() {
    private val viewModel: TimerPageViewModel by viewModels()
}
