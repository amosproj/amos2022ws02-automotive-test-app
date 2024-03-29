package com.amos.infotaimos.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.amos.infotaimos.R
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentLandingPageBinding
import com.amos.infotaimos.model.LandingPageTileAdapter
import com.amos.infotaimos.model.LandingPageTileData
import com.amos.infotaimos.model.LandingPageTileType

class LandingPage : ViewBindingFragment<FragmentLandingPageBinding>() {

    private lateinit var adapter: LandingPageTileAdapter

    /**
     * Applies binding to the landing page
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.fragmentLandingPageTileRecycler.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
        } else {
            binding.fragmentLandingPageTileRecycler.layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.HORIZONTAL,false)
        }
        adapter = LandingPageTileAdapter(LandingPageTileData.APP_TILES) {
            when(it) {
                LandingPageTileType.NAVIGATION -> goToNavigationScreen()
                LandingPageTileType.STEERING_WHEEL -> goToWheelScreen()
                LandingPageTileType.VEHICLE_PROPERTIES -> goToVehiclePropertiesScreen()
                LandingPageTileType.SPEECH_ASSISTANT -> goToSpeechAssistantPageScreen()
                LandingPageTileType.TIMER -> goToTimerScreen()
                LandingPageTileType.APP_SETTINGS -> goToAppSettingsScreen()
                LandingPageTileType.TEST_DRIVE -> goToTestDriveScreen()
            }
        }
        binding.fragmentLandingPageTileRecycler.adapter = adapter
        if (binding.fragmentLandingPageTileRecycler.itemDecorationCount == 0) {
            binding.fragmentLandingPageTileRecycler.addItemDecoration(SpacingDecoration())
            LinearSnapHelper().apply { attachToRecyclerView(binding.fragmentLandingPageTileRecycler) }
        }
        binding.apply {
            landingPage = this@LandingPage
        }
    }

    /**
     * Navigate to the navigation page via the nav_graph
     */
    private fun goToNavigationScreen() {
        findNavController().navigate(R.id.action_landingPage_to_navigationPage)
    }

    /**
     * Navigate to the wheel page via the nav_graph
     */
    private fun goToWheelScreen() {
        findNavController().navigate(R.id.action_landingPage_to_wheelPage)
    }

    /**
     * Navigate to the vehicle properties page via the nav_graph
     */
    private fun goToVehiclePropertiesScreen() {
        findNavController().navigate(R.id.action_landingPage_to_vehiclePropertiesPage)
    }

    /**
     * Navigate to the speech assistant page via the nav_graph
     */
    private fun goToSpeechAssistantPageScreen() {
        findNavController().navigate(R.id.action_landingPage_to_speechAssistantPage)
    }

    /**
     * Navigate to the app settings via the nav_graph
     */
    private fun goToAppSettingsScreen(){
        findNavController().navigate(R.id.action_landingPage_to_appSettings)
    }

    /**
     * Navigate to the timer page via the nav_graph
     */
    private fun goToTimerScreen(){
        findNavController().navigate(R.id.action_landingPage_to_timerPage)
    }

    /**
     * Navigate to the test drive page via the nav_graph
     */
    private fun goToTestDriveScreen(){
        findNavController().navigate(R.id.action_landingPage_to_testDrivePage)
    }
}
