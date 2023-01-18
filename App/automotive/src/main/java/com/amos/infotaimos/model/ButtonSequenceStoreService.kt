package com.amos.infotaimos.model

import android.content.Context
import android.view.KeyEvent
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.UUID

object ButtonSequenceStoreService {

    private var currentButtonSequences: MutableList<ButtonSequence>? = null
    private var defaultSequence: ButtonSequence = ButtonSequence(
        UUID.randomUUID().toString(),
        listOf(
            KeyEvent.KEYCODE_MEDIA_PLAY,
            KeyEvent.KEYCODE_MEDIA_NEXT,
            KeyEvent.KEYCODE_MEDIA_PAUSE
        )
    )

    fun saveButtonSequence(context: Context, buttonSequence: ButtonSequence) {
        if (currentButtonSequences == null) {
            loadButtonSequences(context)
        }
        currentButtonSequences?.add(buttonSequence)
        val json = getMoshiAdapter().toJson(currentButtonSequences)
        context.openFileOutput("ButtonSequence.txt", Context.MODE_PRIVATE).use {
            it?.write(json.toByteArray())
        }
    }

    fun loadButtonSequences(context: Context): MutableList<ButtonSequence>? {
        checkFileExistence(context)
        val json = context.openFileInput("ButtonSequence.txt").bufferedReader().readLine()
        val buttonSequence = getMoshiAdapter().fromJson(json)
        currentButtonSequences = buttonSequence?.toMutableList() ?: mutableListOf(defaultSequence)
        return currentButtonSequences
    }

    fun clearButtonSequences(context: Context) {
        context.openFileOutput("ButtonSequence.txt", Context.MODE_PRIVATE).use {
            it?.write("[]".toByteArray())
        }
        currentButtonSequences = mutableListOf()
        saveButtonSequence(context, defaultSequence)

    }

    private fun getMoshiAdapter() = Moshi.Builder().build().adapter<List<ButtonSequence>>(
        Types.newParameterizedType(
            List::class.java,
            ButtonSequence::class.java
        )
    )

    private fun checkFileExistence(context: Context) {
        try {
            context.openFileInput("ButtonSequence.txt")
        } catch (e: Exception) {
            context.openFileOutput("ButtonSequence.txt", Context.MODE_PRIVATE).use {
                it?.write("[]".toByteArray())
            }
        }
    }
}
