package com.amos.infotaimos.model

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object ButtonSequenceStoreService {

    private var currentButtonSequences: MutableList<ButtonSequence>? = null

    fun saveButtonSequence(context: Context, buttonSequence: ButtonSequence, index: Int? = null) {
        if (currentButtonSequences == null) {
            loadButtonSequences(context)
        }
        if (index == null)
            currentButtonSequences?.add(buttonSequence)
        else
            currentButtonSequences?.add(index, buttonSequence)
        val json = getMoshiAdapter().toJson(currentButtonSequences)
        context.openFileOutput("ButtonSequence.json", Context.MODE_PRIVATE).use {
            it?.write(json.toByteArray())
        }
    }

    fun deleteButtonSequence(context: Context, buttonSequence: ButtonSequence) {
        if (currentButtonSequences == null) {
            loadButtonSequences(context)
        }
        currentButtonSequences?.remove(buttonSequence)
        val json = getMoshiAdapter().toJson(currentButtonSequences)
        context.openFileOutput("ButtonSequence.json", Context.MODE_PRIVATE).use {
            it?.write(json.toByteArray())
        }
    }

    fun loadButtonSequences(context: Context): MutableList<ButtonSequence>? {
        checkFileExistence(context)
        val json = context.openFileInput("ButtonSequence.json").bufferedReader().readLine()
        val buttonSequence = getMoshiAdapter().fromJson(json)
        currentButtonSequences = buttonSequence?.toMutableList() ?: mutableListOf()
        return currentButtonSequences
    }

    private fun getMoshiAdapter() = Moshi.Builder().build().adapter<List<ButtonSequence>>(
        Types.newParameterizedType(
            List::class.java,
            ButtonSequence::class.java
        )
    )

    private fun checkFileExistence(context: Context) {
        try {
            context.openFileInput("ButtonSequence.json")
        } catch (e: Exception) {
            context.openFileOutput("ButtonSequence.json", Context.MODE_PRIVATE).use {
                it?.write("[]".toByteArray())
            }
        }
    }
}
