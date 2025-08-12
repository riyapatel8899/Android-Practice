package com.example.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class CombinedAnalyzer : ImageAnalysis.Analyzer {

    private val luminosityAnalyzer = LuminosityAnalyzer()
    private val qrCodeAnalyzer = QRCodeAnalyzer()

    override fun analyze(image: ImageProxy) {
        // Let only one analyzer close the image
        luminosityAnalyzer.analyze(image)
        // Pass image to second analyzer WITHOUT closing it
        qrCodeAnalyzer.analyzeWithoutClosing(image)
    }
}