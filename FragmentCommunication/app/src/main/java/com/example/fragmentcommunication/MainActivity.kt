package com.example.fragmentcommunication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fragmentcommunication.fragments.FragmentA
import com.example.fragmentcommunication.fragments.FragmentB

class MainActivity : AppCompatActivity(), FragmentA.FragmentAListener, FragmentB.FragmentBListener {
    private lateinit var fragmentA: FragmentA
    private lateinit var fragmentB: FragmentB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fragmentA = FragmentA()
        fragmentB = FragmentB()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_a, fragmentA)
            .replace(R.id.container_b, fragmentB)
            .commit()
    }

    override fun sendFromA(input: String) {
        fragmentB.updateText(input)
    }

    override fun sendFromB(input: String) {
        fragmentA.updateText(input)
    }
}