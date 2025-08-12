package com.example.camera

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraX
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.graphics.Matrix
import android.view.Surface
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import android.view.TextureView
import android.view.ViewGroup
import android.util.Size
import android.widget.ImageView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.impl.ImageAnalysisConfig
import androidx.camera.core.impl.PreviewConfig
import androidx.camera.view.PreviewView
import java.io.File
import java.util.concurrent.Executors
import androidx.camera.core.ImageProxy

class MainActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var flipButton: ImageView
    private lateinit var captureButton: ImageView
    private lateinit var flashButton: ImageView

    companion object{
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val PERMISSIONS_CODE = 1
        private const val FILE_NAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
    }

    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private val executor = Executors.newSingleThreadExecutor()
    private var flashMode = ImageCapture.FLASH_MODE_OFF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previewView = findViewById(R.id.textureView)
        flipButton = findViewById(R.id.flipButton)
        captureButton = findViewById(R.id.captureButton)
        flashButton = findViewById(R.id.flashButton)

        setFlipButtonListener()
        requestCameraPermissions()
        setFlashModeListener()
    }

    private fun hasAllPermissions() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this,it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermissions() {
        if (hasAllPermissions()){
            previewView.post { bindCamera() }
        }
        else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_CODE)
        }
    }

    private fun bindCamera() {
        val previewUseCase = createPreviewUseCase()
        val imageCaptureUseCase = createImageCaptureUseCase()
        val combinedAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(executor, CombinedAnalyzer())
            }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()

            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                previewUseCase,
                imageCaptureUseCase,
                combinedAnalysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_CODE){
            if (hasAllPermissions()) {
                previewView.post { bindCamera() }
            }
            else{
                Toast.makeText(this, "Camera permission not granted", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun updateTextureView(){
        val centerY = previewView.height / 2f
        val centerX = previewView.width / 2f
        val matrix = Matrix()

        val rotationDegrees = when(previewView.display.rotation){
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
//        previewView.setTransform(matrix)
        previewView.scaleType = PreviewView.ScaleType.FILL_CENTER
    }

    private fun createPreviewUseCase(): Preview {
        val preview = Preview.Builder()
            .setTargetResolution(Size(previewView.width, previewView.height))
            .build()

        preview.setSurfaceProvider(previewView.surfaceProvider)

        return preview
    }

    private fun setFlipButtonListener(){
        flipButton.setOnClickListener {
            if (CameraSelector.LENS_FACING_FRONT == lensFacing){
                lensFacing = CameraSelector.LENS_FACING_BACK
                flipButton.setImageDrawable(getDrawable(R.drawable.ic_camera_front_black_24dp))
            }else{
                lensFacing = CameraSelector.LENS_FACING_FRONT
                flipButton.setImageDrawable(getDrawable(R.drawable.ic_camera_rear_black_48dp))
            }
            bindCamera()

        }
    }

    private fun createImageCaptureUseCase(): ImageCapture {
        val imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setFlashMode(flashMode)
            .build()

        captureButton.setOnClickListener {
            val outputDirectory = getOutputDirectory()
            val photoFile = createNewFile(outputDirectory, FILE_NAME, PHOTO_EXTENSION)

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        previewView.post {
                            Toast.makeText(
                                this@MainActivity,
                                "Photo saved: ${photoFile.absolutePath}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        previewView.post {
                            Toast.makeText(
                                this@MainActivity,
                                "Capture failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        }

        return imageCapture
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return mediaDir ?: filesDir
    }

    private fun setFlashModeListener(){
        flashButton.setOnClickListener {
            if (ImageCapture.FLASH_MODE_OFF == flashMode){
                flashMode = ImageCapture.FLASH_MODE_ON
                flashButton.setImageDrawable(getDrawable(R.drawable.ic_flash_on_black_48dp))
            }else{
                flashButton.setImageDrawable(getDrawable(R.drawable.ic_flash_off_black_48dp))
                flashMode = ImageCapture.FLASH_MODE_OFF
            }
            bindCamera()
        }
    }
//
//    private fun createLuminosityAnalyzer(): ImageAnalysis {
//        val analyzer = ImageAnalysis.Builder()
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//            .build()
//            .also {
//                it.setAnalyzer(executor, LuminosityAnalyzer())
//            }
//
//        return analyzer
//    }
//
//    private fun createQRCodeAnalyzer(): ImageAnalysis {
//        val analyzer = ImageAnalysis.Builder()
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//            .build()
//            .also {
//                it.setAnalyzer(executor, QRCodeAnalyzer())
//            }
//
//        return analyzer
//    }

}
















