package com.amos.infotaimos

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager
import com.amos.infotaimos.model.NavigationService
import java.io.File

class NavigationPageViewModel : ViewModel() {

    fun startNavigation(context: Context, delay: Long) {
        NavigationService.startNavigation(CarInstanceManager.getCarInstance(context), delay)
    }

    fun stopNavigation(context: Context, delay: Long) {
        NavigationService.stopNavigation(CarInstanceManager.getCarInstance(context), delay)
    }

    fun speechAnnouncement(context: Context, delay: Long) {
        val mediaPlayer = MediaPlayer.create(context,R.raw.navigation_announcement)//MediaPlayer()

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        )
        // mediaPlayer.setDataSource("/home/daniel/Downloads/navigation_announcement.mp3")
        /*mediaPlayer.setDataSource(context, Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
            context.resources.getResourcePackageName(R.raw.navigation_announcement) +
            context.resources.getResourceTypeName(R.raw.navigation_announcement) +
            context.resources.getResourceEntryName(R.raw.navigation_announcement)))

        mediaPlayer.prepare()

         */

        NavigationService.speechAnnouncement(
            //MediaPlayer.create(context,R.raw.navigation_announcement),
            mediaPlayer,
            delay
        )
    }
}
