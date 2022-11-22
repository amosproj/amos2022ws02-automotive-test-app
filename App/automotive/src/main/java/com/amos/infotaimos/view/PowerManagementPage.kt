package com.amos.infotaimos.view

import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentPowerManagementPageBinding
import com.amos.infotaimos.viewmodel.PowerManagementPageViewModel

class PowerManagementPage : ViewBindingFragment<FragmentPowerManagementPageBinding>() {
    private val viewModel: PowerManagementPageViewModel by viewModels()
}
