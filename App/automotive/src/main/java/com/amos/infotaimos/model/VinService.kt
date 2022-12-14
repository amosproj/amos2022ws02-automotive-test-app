package com.amos.infotaimos.model

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException

object VinService {
    private const val TAG = "VIN_SERVICE"

    fun loadData(context: Context) : String {
        try{
            Log.d(TAG, "load vin from disk")
            return context.openFileInput("Vin.txt")?.bufferedReader()?.readLine().toString()
        }
        catch(e : FileNotFoundException){
            Log.d(TAG, "no vin could be found")
            return " "
        }
    }

    fun saveData(context: Context, s: String) {
        Log.d(TAG, "$s is saved in Vin.txt")
        context.openFileOutput("Vin.txt", Context.MODE_PRIVATE).use {
            it?.write(s.toByteArray())
        }
    }
}