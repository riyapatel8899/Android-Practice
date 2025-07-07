package com.example.uberclone.utils

import android.view.View
import com.example.uberclone.services.MyFirebaseMessagingService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.content.Context
import android.widget.Toast
import com.example.uberclone.models.Token

object UserUtils {

    fun updateuser(
        view: View?,
        upadateData: Map<String, Any>
    ){
        FirebaseDatabase.getInstance()
            .getReference(Constants.DRIVER_INFO_REFERENCE)
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .updateChildren(upadateData)
            .addOnSuccessListener {
                Snackbar.make(view!!, "Data Updated Successfully!", Snackbar.LENGTH_LONG).show()
            }.addOnFailureListener {
                Snackbar.make(view!!,it.message!!, Snackbar.LENGTH_LONG).show()
            }
    }

    fun updateToken(context: Context, token: String) {
        val takeModel = Token(token)

        FirebaseDatabase.getInstance()
            .getReference(Constants.TOKEN_REFERENCE)
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .setValue(token)
            .addOnFailureListener { e -> Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()}
    }
}