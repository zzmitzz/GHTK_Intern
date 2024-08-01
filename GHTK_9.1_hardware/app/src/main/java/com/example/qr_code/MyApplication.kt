package com.example.qr_code

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraX
import androidx.camera.core.CameraXConfig



// Log only the error relevant to the CameraX library
class MyApplication : Application(), CameraXConfig.Provider {
    override fun getCameraXConfig(): CameraXConfig {
        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig())
            .setMinimumLoggingLevel(android.util.Log.ERROR).build()
    }

}