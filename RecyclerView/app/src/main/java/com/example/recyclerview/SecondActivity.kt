package com.example.recyclerview

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclerview.Utils.Constants

class SecondActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleTextView = findViewById(R.id.title)
        descriptionTextView = findViewById(R.id.description)

        val data =  intent.extras
        data?.let {
            titleTextView.text = it.getString(Constants.KEY_TITLE)
            descriptionTextView.text = it.getString(Constants.KEY_DESCRIPTION)
        }
    }
}