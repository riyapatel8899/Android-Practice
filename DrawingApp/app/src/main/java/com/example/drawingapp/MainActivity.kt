package com.example.drawingapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ImageFormat.JPEG
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.internal.ManufacturerUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var drawingView: DrawingView

    private lateinit var brushButton: ImageButton
    private lateinit var purpleButton: ImageButton
    private lateinit var greenButton: ImageButton
    private lateinit var blueButton: ImageButton
    private lateinit var orangeButton: ImageButton
    private lateinit var redButton: ImageButton
    private lateinit var undoButton: ImageButton
    private lateinit var colorPickerButton: ImageButton
    private lateinit var galleryButton: ImageButton
    private lateinit var saveButton: ImageButton

    private var isSavingImage = false

    private val openGalleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            findViewById<ImageView>(R.id.gallery_image).setImageURI(result.data?.data)
        }

    private val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value

//                if (isGranted && permissionName == android.Manifest.permission.READ_EXTERNAL_STORAGE) {
//                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
//
//                    val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                    openGalleryLauncher.launch(pickIntent)
//                }
//                else {
//                    if (permissionName == android.Manifest.permission.READ_EXTERNAL_STORAGE) {
//                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                    }
//                }
                val validPermissions = listOf(
                    if (android.os.Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                if (isGranted && validPermissions.contains(permissionName)) {
                    if (isSavingImage) {
                        val layout = findViewById<ConstraintLayout>(R.id.constraint_l3)
                        val bitmap = getBitmapFromView(layout)
                        CoroutineScope(IO).launch {
                            saveImage(bitmap)
                        }
                    } else {
                        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        openGalleryLauncher.launch(pickIntent)
                    }
                } else if (validPermissions.contains(permissionName)) {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        purpleButton = findViewById(R.id.purple_button)
        redButton = findViewById(R.id.red_button)
        greenButton = findViewById(R.id.green_button)
        blueButton = findViewById(R.id.blue_button)
        orangeButton = findViewById(R.id.orange_button)
        undoButton = findViewById(R.id.undo_button)
        colorPickerButton = findViewById(R.id.color_picker_button)
        galleryButton = findViewById(R.id.gallery_button)
        saveButton = findViewById(R.id.save_button)

        brushButton = findViewById(R.id.brush_button)
        drawingView = findViewById(R.id.drawing_view)
        drawingView.changeBrushSize(23.toFloat())

        brushButton.setOnClickListener {
            showBrushChooserDialog()
        }

        purpleButton.setOnClickListener(this)
        redButton.setOnClickListener(this)
        orangeButton.setOnClickListener(this)
        greenButton.setOnClickListener(this)
        blueButton.setOnClickListener(this)
        undoButton.setOnClickListener(this)
        colorPickerButton.setOnClickListener(this)
        galleryButton.setOnClickListener(this)
        saveButton.setOnClickListener(this)
    }

    private fun showBrushChooserDialog() {
        val brushDialog = Dialog(this@MainActivity)
        brushDialog.setContentView(R.layout.dialog_brush)
        val seekBarProgress = brushDialog.findViewById<SeekBar>(R.id.dialog_seek_bar)
        val showProgressTv = brushDialog.findViewById<TextView>(R.id.dialog_text_view_progress)

        seekBarProgress.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                if (seekBar != null) {
                    drawingView.changeBrushSize(seekBar.progress.toFloat())
                }
                if (seekBar != null) {
                    showProgressTv.text = seekBar.progress.toString()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.purple_button -> {
                drawingView.setColor("#D14EF6")
            }
            R.id.red_button -> {
                drawingView.setColor("#FA5B68")
            }
            R.id.orange_button -> {
                drawingView.setColor("#EFB041")
            }
            R.id.green_button -> {
                drawingView.setColor("#2DC40B")
            }
            R.id.blue_button -> {
                drawingView.setColor("#2F6FF1")
            }
            R.id.undo_button -> {
                drawingView.undoPath()
            }
            R.id.color_picker_button -> {
                showColorPickerDialog()
            }
            R.id.gallery_button -> {
//                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                    requestStoragePermission()
//                }
//                else {
//                    val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                    openGalleryLauncher.launch(pickIntent)
//                }
                isSavingImage = false
                val permission = if (android.os.Build.VERSION.SDK_INT >= 33) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }

                if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                    requestStoragePermission()
                } else {
                    val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    openGalleryLauncher.launch(pickIntent)
                }
            }
            R.id.save_button -> {
                isSavingImage = true
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    requestStoragePermission()
                }
                else {
                    val layout = findViewById<ConstraintLayout>(R.id.constraint_l3)
                    val bitmap = getBitmapFromView(layout)

                    CoroutineScope(IO).launch {
                        saveImage(bitmap)
                    }
                }
            }
        }
    }
    private fun showColorPickerDialog() {
        val dialog = AmbilWarnaDialog(this, Color.GREEN, object: OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                drawingView.setColor(color)
            }

        })
        dialog.show()
    }

    private fun requestStoragePermission() {
        val permissions = if (android.os.Build.VERSION.SDK_INT >= 33) {
            arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
            showRationaleDialog(permissions[0])
        } else {
            requestPermission.launch(permissions)
        }

//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//            this, android.Manifest.permission.READ_EXTERNAL_STORAGE
//        )
//    ){
//            showRationaleDialog()
//        }
//        else {
//            requestPermission.launch(
//                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//            )
//        }
    }

//    private fun showRationaleDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Storage permission")
//            .setMessage("We need this permission in order to access the internal storage")
//            .setPositiveButton(R.string.dialog_yes) { dialog, _ ->
//                requestPermission.launch(
//                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                )
//                dialog.dismiss()
//            }
//        builder.create().show()
//    }

    private fun showRationaleDialog(permission: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Storage Permission")
            .setMessage("We need this permission to access your gallery.")
            .setPositiveButton("Yes") { dialog, _ ->
                requestPermission.launch(arrayOf(permission))
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private suspend fun saveImage(bitmap: Bitmap) {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/saved_images")
        myDir.mkdir()
        val generator = java.util.Random()
        var n = 10000
        n = generator.nextInt(n)
        val outPutFile = File(myDir, "Image-$n.jpg")
        if (outPutFile.exists()) {
            outPutFile.delete()
        }
        else {
            try {
                val out = FileOutputStream(outPutFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
                out.close()
            }
            catch (e: Exception) {
                e.stackTrace
            }

            withContext(Main) {
                Toast.makeText(this@MainActivity, "${outPutFile.absolutePath} saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

