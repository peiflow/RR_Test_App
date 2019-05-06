package com.asociacion.rr_test_app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

import kotlinx.android.synthetic.main.activity_scan.*
import java.io.IOException
import java.lang.Exception
import java.util.jar.Manifest

class ScanActivity : AppCompatActivity() {
    lateinit var cameraView: SurfaceView
    lateinit var barcode: BarcodeDetector
    lateinit var cameraSource: CameraSource
    lateinit var holder: SurfaceHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        setSupportActionBar(toolbar)

        cameraView = findViewById(R.id.cameraView)
        cameraView.setZOrderMediaOverlay(true)
        holder = cameraView.holder
        barcode = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build()

        if (!barcode.isOperational) {
            Toast.makeText(applicationContext, "Couldn´t setup the detector", Toast.LENGTH_LONG).show()
            this.finish()
        }

        cameraSource = CameraSource.Builder(this, barcode)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedFps(24f)
            .setAutoFocusEnabled(true)
            .setRequestedPreviewSize(1920, 1024)
            .build()

        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ContextCompat.checkSelfPermission(this@ScanActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.holder)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }
        })

        barcode.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() > 0) {
                    val intent = Intent()
                    intent.putExtra("barcode", barcodes.valueAt(0))
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        })
        /*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

}
