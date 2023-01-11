package com.amos.infotaimos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestDrivePageViewModel : ViewModel() {
    val _tapText: MutableLiveData<String> = MutableLiveData("")
    val tapText: LiveData<String>
        get() = _tapText
}