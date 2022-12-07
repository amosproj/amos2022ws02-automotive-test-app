package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentAppSettingsBinding
import com.amos.infotaimos.viewmodel.AppSettingsViewModel


class AppSettings : ViewBindingFragment<FragmentAppSettingsBinding>() {
    private val viewModel: AppSettingsViewModel by viewModels()

    /**
     * Creates the view of the AppSettings page and binds the theme mode of the app.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appThemeModeTile.root.setOnClickListener { viewModel.changeThemeMode() }
    }
}
