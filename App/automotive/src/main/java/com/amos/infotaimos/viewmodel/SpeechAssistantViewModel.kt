package com.amos.infotaimos.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amos.infotaimos.model.SpeechService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpeechAssistantViewModel : ViewModel() {
    var active : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false);
    private val TAG  = "SPEECH ASSISTANT";

    fun startPTT(context: Context, delay: Long) {
        if(!SpeechService.getUsed()){
            SpeechService.setUsed(true)
            viewModelScope.launch {
                delay(delay)
                active.value = true;
                SpeechService.startPTT(context)
                active.value = false
                SpeechService.setUsed(false)
            }
        }
        else{
           Log.d(TAG, "Speech assistant is already in use" )
        }
    }

    fun startTTT(context: Context, delay: Long){
        if(!SpeechService.getUsed()){
            SpeechService.setUsed(true)
            viewModelScope.launch {
                delay(delay)
                active.value = true;
                SpeechService.startTTT(context)
                active.value = false
                SpeechService.setUsed(false)
            }
        }
        else{
            Log.d(TAG, "Speech assistant is already in use" )
        }
    }
}































