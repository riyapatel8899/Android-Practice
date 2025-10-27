package com.example.contacts.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contacts.model.Contact
import com.example.contacts.utils.Constants
import android.util.Log

class DatabaseHandler(val context: Context) : SQLiteOpenHelper(context,Constants.DATABASE_NAME,null, Constants.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE_CONTACTS = "CREATE TABLE ${Constants.TABLE_NAME} (" +
                "${Constants.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Constants.KEY_NAME} TEXT," +
                "${Constants.KEY_PHONE_NUMBER} TEXT)"

        Log.d("DatabaseHandler", "Creating table with: $CREATE_TABLE_CONTACTS")
        db?.execSQL(CREATE_TABLE_CONTACTS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS ${Constants.TABLE_NAME}")

        onCreate(db)
    }

    fun addContact(contact: Contact) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Constants.KEY_NAME, contact.name)
        values.put(Constants.KEY_PHONE_NUMBER, contact.phoneNumber)

        db.insert(Constants.TABLE_NAME, null, values)
        db.close()
    }

    fun getContact(id: Int): Contact {
        val db = this.readableDatabase

        val cursor = db.query(Constants.TABLE_NAME,
            arrayOf(Constants.KEY_ID, Constants.KEY_NAME, Constants.KEY_PHONE_NUMBER),
            Constants.KEY_ID + "=?", arrayOf(id.toString()), null, null, null,null)

        cursor?.let {
            it.moveToFirst()
        }
        return  Contact(cursor.getString(0).toInt(),cursor.getString(1),cursor.getString(2))
    }

    fun getAllContacts(): MutableList<Contact> {
        val db = this.readableDatabase

        val contactList = mutableListOf<Contact>()

        val selectAll = "SELECT * FROM ${Constants.TABLE_NAME}"
        val cursor = db.rawQuery(selectAll, null)

        if (cursor.moveToFirst()) {
            do {
//                val contact = Contact()
//                contact.id = cursor.getString(0).toInt()
//                contact.name = cursor.getString(1)
//                contact.phoneNumber = cursor.getString(2)
                val contact = Contact(
                    id = cursor.getString(0).toInt(),
                    name = cursor.getString(1),
                    phoneNumber = cursor.getString(2)
                )

                contactList.add(contact)
            }while (cursor.moveToNext())
        }
        return contactList
    }

    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Constants.KEY_NAME, contact.name)
        values.put(Constants.KEY_PHONE_NUMBER,contact.phoneNumber)

        return db.update(Constants.TABLE_NAME, values,Constants.KEY_ID + "=?",
            arrayOf(contact.id.toString())
        )
    }

    fun deleteContact(contact: Contact) {
        val db = this.writableDatabase
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?",
            arrayOf(contact.id.toString())
        )
    }

    fun getContactsCount(): Int {
        val countQuery = "SELECT * FROM ${Constants.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery,null)

        return cursor.count
    }
}
