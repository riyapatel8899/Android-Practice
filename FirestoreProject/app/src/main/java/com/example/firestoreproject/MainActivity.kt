package com.example.firestoreproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firestoreproject.classes.Note
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var saveButton: Button
    private lateinit var editTextPriority: EditText
    private lateinit var editTextTags: EditText

    private val db:FirebaseFirestore = FirebaseFirestore.getInstance()
    private val noteBookRef: CollectionReference = db.collection("Notebook")
    private var lastResult: DocumentSnapshot? = null
    private val TAG = "MainActivity"

    private lateinit var loadButton: Button
    private lateinit var textViewData: TextView

    private val KEY_TITLE = "title"
    private val KEY_DESCRIPTION = "description"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        saveButton = findViewById(R.id.add_button)
        loadButton = findViewById(R.id.load_button)
        textViewData = findViewById(R.id.text_view_data)
        editTextPriority = findViewById(R.id.edit_text_priority)
        editTextTags = findViewById(R.id.edit_text_tags)

        saveButton.setOnClickListener {
            addNote()
        }

        loadButton.setOnClickListener {
            loadNotes()
        }

        updateObjects()

    }

    override fun onStart() {
        super.onStart()


    }

    private fun addNote() {
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()

        if (editTextPriority.text.toString().isEmpty()) {
            editTextPriority.setText("0")
        }

        val tagsArray = editTextTags.text.toString().trim().split(",")
        val tags = mutableMapOf<String, Boolean>()

        for (tag in tagsArray) {
            tags[tag] = true
        }

        val priority = editTextPriority.text.toString().toInt()
        val note = Note(title, description, priority, tags)
        noteBookRef.add(note)
            .addOnSuccessListener {
            Toast.makeText(this@MainActivity, "Note added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity, "Error: note was not added", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadNotes() {
        noteBookRef.whereEqualTo("tags.tag1", true)
            .get()
            .addOnSuccessListener {
                var data = ""
                 for (documentSnapshot in it) {
                     val note = documentSnapshot.toObject(Note::class.java)
                     note.id = documentSnapshot.id

                     data += "ID: ${note.id}"
                     for (tag in note.tags?.keys!!) {
                         data += "\n- $tag"
                     }
                     data += "\n\n"
                 }
                textViewData.text = data
            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateObjects() {
        noteBookRef.document()
            .update("tags.tag1", FieldValue.delete())
    }
}