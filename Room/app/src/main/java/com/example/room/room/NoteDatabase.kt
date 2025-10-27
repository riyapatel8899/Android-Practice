package com.example.room.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context) : NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "note_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}