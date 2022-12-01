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

    /**
     * Construct landing page fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return landing page view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingObject = FragmentLandingPageBinding.inflate(inflater, container, false)
        binding = bindingObject
        return bindingObject.root
    }

    /**
     * Applies binding to the landing page
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            landingPage = this@LandingPage
        }
    }

    /**
     * Navigate to the navigation page via the nav_graph
     */
    fun goToNavigationScreen() {
        findNavController().navigate(R.id.action_landingPage_to_navigationPage)
    }

    /**
     * Navigate to the wheel page via the nav_graph
     */
    fun goToWheelScreen() {
        findNavController().navigate(R.id.action_landingPage_to_wheelPage)
    }

    /**
     * Navigate to the power management page via the nav_graph
     */
    fun goToPowerManagementScreen() {
        findNavController().navigate(R.id.action_landingPage_to_powerManagementPage)
    }

    /**
     * Navigate to the vehicle properties page via the nav_graph
     */
    fun goToVehiclePropertiesScreen() {
        findNavController().navigate(R.id.action_landingPage_to_vehiclePropertiesPage)
    }

    /**
     * Navigate to the media page via the nav_graph
     */
    fun goToMediaPageScreen() {
        findNavController().navigate(R.id.action_landingPage_to_mediaPage)
    }

    /**
     * Navigate to the app settings via the nav_graph
     */
    fun goToAppSettingsScreen(){
        findNavController().navigate(R.id.action_langingPage_to_appSettings)
    }
}
