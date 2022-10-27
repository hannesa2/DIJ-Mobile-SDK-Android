package com.dji.sdk.sample.internal.controller

import android.app.Application
import android.content.Context
import com.dji.sdk.sample.internal.utils.ToastUtils
import info.hannes.logcat.LoggingApplication
import com.secneo.sdk.Helper
import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer
import dji.sdk.base.BaseProduct
import dji.sdk.sdkmanager.DJISDKManager
import dji.sdk.sdkmanager.BluetoothProductConnector
import dji.sdk.products.Aircraft
import dji.sdk.products.HandHeld

class DJISampleApplication : LoggingApplication(ToastUtils::class.java) {

    override fun attachBaseContext(paramContext: Context) {
        super.attachBaseContext(paramContext)
        Helper.install(this)
        instance = this
    }

    companion object {
        private var localEventBus = Bus(ThreadEnforcer.ANY)
        @JvmName("getEventBus")
        fun getEventBus() = localEventBus

        var instance: Application? = null
            private set

        /**
         * Gets instance of the specific product connected after the
         * API KEY is successfully validated. Please make sure the
         * API_KEY has been added in the Manifest
         */
        @JvmStatic
        @get:Synchronized
        val productInstance: BaseProduct?
            get() = DJISDKManager.getInstance().product

        @JvmStatic
        @get:Synchronized
        val bluetoothProductConnector: BluetoothProductConnector
            get() = DJISDKManager.getInstance().bluetoothProductConnector
        val isAircraftConnected: Boolean
            get() = productInstance != null && productInstance is Aircraft
        private val isHandHeldConnected: Boolean
            get() = productInstance != null && productInstance is HandHeld

        @JvmStatic
        @get:Synchronized
        val aircraftInstance: Aircraft?
            get() = if (!isAircraftConnected) {
                null
            } else productInstance as Aircraft?

        @get:Synchronized
        val handHeldInstance: HandHeld?
            get() = if (!isHandHeldConnected) {
                null
            } else productInstance as HandHeld?
    }
}