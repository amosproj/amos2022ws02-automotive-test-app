package com.amos.infotaimos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amos.infotaimos.databinding.FragmentLandingPageBinding

class LandingPage : Fragment() {

    private var binding: FragmentLandingPageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingObject = FragmentLandingPageBinding.inflate(inflater, container, false)
        binding = bindingObject
        return bindingObject.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            landingPage = this@LandingPage
        }
    }
    fun goToNavigationScreen() {
        findNavController().navigate(R.id.action_landingPage_to_navigationPage)
    }
}
