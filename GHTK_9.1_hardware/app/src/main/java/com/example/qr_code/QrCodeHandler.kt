package com.example.qr_code

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.google.mlkit.vision.barcode.common.Barcode

class QrCodeHandler(context: Context, barcode: Barcode?) {
    var qrContent: String = ""
    var qrCodeTouchCallback = {  false} //no-op

    init {
        when (barcode!!.valueType) {
            Barcode.TYPE_URL -> {
                qrContent = barcode.url!!.url!!
                qrCodeTouchCallback = {
                    val openBrowserIntent = Intent(Intent.ACTION_VIEW)
                    openBrowserIntent.data = Uri.parse(qrContent)
                    startActivity(context,openBrowserIntent, null)
                    true
                // return true from the callback to signify the event was handled
                }
            }
            // Add other QR Code types here to handle other types of data,
            // like Wifi credentials.
            else -> {
                qrContent = "It's might be a plain text: ${barcode.rawValue.toString()}"
            }
        }

    }

}
