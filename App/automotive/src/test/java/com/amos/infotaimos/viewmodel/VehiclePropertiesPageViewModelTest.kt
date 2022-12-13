package com.amos.infotaimos.viewmodel
import com.amos.infotaimos.view.VehiclePropertiesPage
import org.junit.Test


class VehiclePropertiesPageViewModelTest {

    /**
     * Test the ability to read and write to the battery.
     * Energy permission needs to be granted beforehand
     */
    @Test
    fun readBattery() {
        val view = VehiclePropertiesPage()
        val viewModel = VehiclePropertiesPageViewModel()
        view.context?.let {
            viewModel.setPropertyManager(it)
            viewModel.setBatteryWH(0f)
            val batteryAfter = viewModel.getBatteryWH()
            assert(0f == batteryAfter)
        }
    }
}
