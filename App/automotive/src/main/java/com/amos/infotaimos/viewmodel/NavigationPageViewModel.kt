package com.amos.infotaimos.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.R
import com.amos.infotaimos.model.CarInstanceManager
import com.amos.infotaimos.model.NavigationService

class NavigationPageViewModel : ViewModel() {

    fun startNavigation(context: Context, delay: Long) {
        NavigationService.startNavigation(CarInstanceManager.getCarInstance(context), delay)
    }

    fun stopNavigation(context: Context, delay: Long) {
        NavigationService.stopNavigation(CarInstanceManager.getCarInstance(context), delay)
    }

    fun speechAnnouncement(context: Context, delay: Long) {

        val announcementUri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.resources.getResourcePackageName(R.raw.navigation_announcement))
            .appendPath(context.resources.getResourceTypeName(R.raw.navigation_announcement))
            .appendPath(context.resources.getResourceEntryName(R.raw.navigation_announcement))
            .build()

        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()
            )
            setDataSource(context, announcementUri)
            prepare()
        }

        NavigationService.speechAnnouncement(
            mediaPlayer,
            delay
        )
    }
}
