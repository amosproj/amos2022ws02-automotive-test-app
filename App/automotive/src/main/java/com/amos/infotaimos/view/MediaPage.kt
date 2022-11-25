package com.amos.infotaimos.view


import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentMediaPageBinding
import com.amos.infotaimos.viewmodel.MediaPageViewModel


class MediaPage : ViewBindingFragment<FragmentMediaPageBinding>() {
    private val viewModel: MediaPageViewModel by viewModels()
}
