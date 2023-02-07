package com.amos.infotaimos.model

import com.amos.infotaimos.R

enum class LandingPageTileType {
    NAVIGATION,
    STEERING_WHEEL,
    VEHICLE_PROPERTIES,
    SPEECH_ASSISTANT,
    TIMER,
    APP_SETTINGS,
    TEST_DRIVE;

    fun getID(): Int {
        return when(this) {
            NAVIGATION -> 42
            STEERING_WHEEL -> 43
            VEHICLE_PROPERTIES -> 45
            SPEECH_ASSISTANT -> 47
            TIMER -> 48
            APP_SETTINGS -> 49
            TEST_DRIVE -> 50
        }
    }
}
data class LandingPageTileData(val type: LandingPageTileType) {

    fun getStringResource(): Int {
        return when (type) {
            LandingPageTileType.NAVIGATION -> R.string.navigation
            LandingPageTileType.STEERING_WHEEL -> R.string.steering_wheel
            LandingPageTileType.VEHICLE_PROPERTIES -> R.string.vehicle_properties
            LandingPageTileType.SPEECH_ASSISTANT -> R.string.speech_assistant
            LandingPageTileType.TIMER -> R.string.timer
            LandingPageTileType.APP_SETTINGS -> R.string.app_settings
            LandingPageTileType.TEST_DRIVE -> R.string.test_drive
        }
    }

    fun getIconResource(): Int {
        return when(type) {
            LandingPageTileType.NAVIGATION -> R.drawable.map_icon
            LandingPageTileType.STEERING_WHEEL -> R.drawable.steering_wheel_icon
            LandingPageTileType.VEHICLE_PROPERTIES -> R.drawable.vehicle_properties_icon
            LandingPageTileType.SPEECH_ASSISTANT -> R.drawable.speech_assistant_icon_24
            LandingPageTileType.TIMER -> R.drawable.timer_icon
            LandingPageTileType.APP_SETTINGS -> R.drawable.settings_gear_icon
            LandingPageTileType.TEST_DRIVE -> R.drawable.road
        }
    }

    fun getColor(): Int {
        return when(type) {
            LandingPageTileType.NAVIGATION -> R.color.landing_green
            LandingPageTileType.STEERING_WHEEL -> R.color.landing_blue
            LandingPageTileType.VEHICLE_PROPERTIES -> R.color.landing_red
            LandingPageTileType.SPEECH_ASSISTANT -> R.color.landing_yellow
            LandingPageTileType.TIMER -> R.color.landing_orange
            LandingPageTileType.APP_SETTINGS -> R.color.landing_pink
            LandingPageTileType.TEST_DRIVE -> R.color.landing_light_blue
        }
    }

    companion object {
        val APP_TILES = LandingPageTileType.values().map { LandingPageTileData(it) }
    }
}