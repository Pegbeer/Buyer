package com.pegbeer.buyer

import android.app.Application
import android.util.Log
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.io.FileInputStream
import java.util.Properties


@HiltAndroidApp
class BuyerApp : Application() {

    private val key:String
        get() = getAppCenterKey()

    private fun getAppCenterKey(): String {
        return try{
            val properties = Properties()
            val inputStream = assets.open("app.properties")
            properties.load(inputStream)
            properties.getValue("app-center-key").toString()
        }catch (ex:Exception){
            Log.d(TAG, "getAppCenterKey: ${ex.message}")
            ""
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppCenter.start(this,key,Analytics::class.java,Crashes::class.java)
    }

    companion object{
        const val TAG = "BuyerApp"
    }
}