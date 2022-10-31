package com.amos.infotaimos

import android.car.Car
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(context: Context): ViewModel() {
    val carData = MutableLiveData(Car.createCar(context))
}