package com.amos.infotaimos.view

import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentAppSettingsBinding
import com.amos.infotaimos.viewmodel.AppSettingsViewModel

class AppSettings : ViewBindingFragment<FragmentAppSettingsBinding>() {
    private val viewModel: AppSettingsViewModel by viewModels()
}