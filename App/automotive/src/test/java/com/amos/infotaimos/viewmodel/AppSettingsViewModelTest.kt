package com.amos.infotaimos.viewmodel
import androidx.appcompat.app.AppCompatDelegate
import org.junit.Test

class AppSettingsViewModelTest {

    /**
     * Test the ability to change between light and dark mode.
     */
    @Test
    fun changeThemeMode() {
        val viewModel = AppSettingsViewModel()
        viewModel.changeThemeMode()
        val theme1 = AppCompatDelegate.getDefaultNightMode()
        viewModel.changeThemeMode()
        val theme2 = AppCompatDelegate.getDefaultNightMode()
        assert(theme1 != theme2)
        viewModel.changeThemeMode()
        val theme3 = AppCompatDelegate.getDefaultNightMode()
        assert(theme1 == theme3)
        assert(theme2 != theme3)
    }
}
