package com.example.camera


import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class LuminosityAnalyzer : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)
        val luminance = data.map { it.toInt() and 0xFF }.average()
        Log.d("LuminosityAnalyzer", "Average luminance: $luminance")
        image.close() // ✅ Close image only here
    }
}
