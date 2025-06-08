package com.example.contacts

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contacts.data.DatabaseHandler
import com.example.contacts.model.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var textViewData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewData = findViewById(R.id.text_view_data)
        val db = DatabaseHandler(this)

        db.addContact(Contact("Riya", "123456"))
        db.addContact(Contact("Yash", "456789"))
        db.addContact(Contact("Jeet", "963258"))
        db.addContact(Contact("Babu", "887852"))

        val contactList = db.getAllContacts()
        var data = ""
        for (contact in contactList) {
            data += "\nName: ${contact.name} " +
                    "\nPhoneNumber: ${contact.phoneNumber}\n"
        }
        textViewData.text = data
        textViewData.append("\nThe number of contacts: ${db.getContactsCount()}")
    }
}