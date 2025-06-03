package com.example.bottomsheet

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bottomsheet.dialogs.MyBottomSheetDialog

class MainActivity : AppCompatActivity(), MyBottomSheetDialog.BottomSheetListener {

    private lateinit var openDialogButton: Button
    private lateinit var textViewButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        openDialogButton = findViewById(R.id.button_open_dialog)
        textViewButton = findViewById(R.id.textview)

        openDialogButton.setOnClickListener {
            val myBottomSheetDialog = MyBottomSheetDialog()
            myBottomSheetDialog.show(supportFragmentManager, "Dialog")
        }
    }

    override fun onButtonClicked(input: String) {
        textViewButton.text = input
    }
}