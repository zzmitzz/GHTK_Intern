package com.example.qr_code

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    // Restrict the camera execution to one thread
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var barcodeScanner: BarcodeScanner
    private val timeInterval = 3000L
    private var startTime = System.currentTimeMillis()
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value) {
                    permissionGranted = false
                }
            }
            if (!permissionGranted) {
                Toast
                    .makeText(
                        baseContext,
                        "Permission request denied",
                        Toast.LENGTH_SHORT,
                    ).show()
            } else {
                initCamera()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkPermission()) {
            requestCameraPermission()
        } else {
            initCamera()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun requestCameraPermission() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun checkPermission() =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun initCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        lifecycleScope.launch {
            setupCamera()
        }
    }

    @Synchronized
    private fun setupCamera() {
        val cameraController = LifecycleCameraController(baseContext)
        val previewView = findViewById<PreviewView>(R.id.previewView)
        val options =
            BarcodeScannerOptions
                .Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        barcodeScanner = BarcodeScanning.getClient(options)
        cameraController.bindToLifecycle(this)
        previewView.controller = cameraController
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(this),
            ) { result ->
                var currentTime = System.currentTimeMillis()
                Log.d("TAG", "setupCamera: $currentTime")
                if(currentTime - startTime > timeInterval) {
                    startTime = currentTime
                    val barcodeResults = result?.getValue(barcodeScanner)
                    if ((barcodeResults == null) ||
                        (barcodeResults.size == 0) ||
                        (barcodeResults.first() == null)
                    ) {
                        previewView.overlay.clear() // no-op
                        return@MlKitAnalyzer
                    }

                    val qrResult = QrCodeHandler(this, barcodeResults[0])
                    previewView.overlay.clear()
                    if (!qrResult.qrCodeTouchCallback.invoke()) {
                        Toast.makeText(this, qrResult.qrContent, Toast.LENGTH_SHORT).show()
                    }
                }
            },
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        barcodeScanner.close()
    }

    companion object {
        const val CAMERA_REQUEST_CODE = 1
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
            ).toTypedArray()
    }
}
