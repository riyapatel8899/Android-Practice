package com.example.firestoreproject.classes

import android.icu.text.CaseMap.Title
import com.google.firebase.firestore.Exclude

data class Note(val title: String, val description: String, val priority: Int, val tags: MutableMap<String, Boolean>?) {
    constructor(): this("", "", 0, null)

    @Exclude
    var id: String = ""
}