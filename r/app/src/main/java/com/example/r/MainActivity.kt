package com.example.r

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

//        fetchData()
//        sendPostRequest()
//        sendPutRequest()
        sendDeleteRequest()
    }


//    private fun fetchData() {
//        val request = Request.Builder()
//            .url("https://jsonplaceholder.typicode.com/posts/1")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                runOnUiThread {
//                    textView.text = "Request failed: ${e.message}"
//                }
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.body?.string()?.let { responseBody ->
//                    runOnUiThread {
//                        textView.text = responseBody
//                    }
//                }
//            }
//        })
//    }
//
//
//    private fun sendPostRequest() {
//        val url = "https://jsonplaceholder.typicode.com/posts"
//
//        val jsonObject = JSONObject()
//        jsonObject.put("name", "John")
//        jsonObject.put("age", 25)
//
//        val requestBody = jsonObject.toString()
//            .toRequestBody("application/json; charset=utf-8".toMediaType())
//
//        val request = Request.Builder()
//            .url(url)
//            .post(requestBody)
//            .build()
//
//        client.newCall(request).enqueue(callback("POST"))
//    }

//    private fun callback(type: String) = object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            Log.e("$type Request", "Failed: ${e.message}")
//            runOnUiThread {
//                textView.text = "Request failed: ${e.message}"
//            }
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            if (response.isSuccessful) {
//                val body = response.body?.string()
//                Log.d("$type Request", "Success: $body")
//                runOnUiThread {
//                    textView.text = "Success: $body"
//                }
//            } else {
//                Log.e("$type Request", "Error: HTTP ${response.code}")
//                runOnUiThread {
//                    textView.text = "Error: HTTP ${response.code}"
//                }
//            }
//        }
//    }

//    private fun sendPutRequest() {
//        val url = "https://jsonplaceholder.typicode.com/posts/1"
//
//        val jsonObject = JSONObject()
//        jsonObject.put("userId", 1)
//        jsonObject.put("id", 1)
//        jsonObject.put("title", "Updated Title")
//        jsonObject.put("body", "Updated content")
//
//        val requestBody = jsonObject.toString()
//            .toRequestBody("application/json; charset=utf-8".toMediaType())
//
//        val request = Request.Builder()
//            .url(url)
//            .put(requestBody)
//            .build()
//
//        client.newCall(request).enqueue(callback("PUT"))
//    }
//
//    private fun callback(type: String) = object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            Log.e("$type Request", "Failed: ${e.message}")
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            val body = response.body?.string()
//            Log.d("$type Request", "Raw response: $body")
//
//            runOnUiThread {
//                textView.text = if (body.isNullOrBlank()) {
//                    "Response body is empty"
//                } else {
//                    "Response:\n$body"
//                }
//            }
//        }
//    }

    private fun sendDeleteRequest() {
        val url = "https://jsonplaceholder.typicode.com/posts/101"

        val request = Request.Builder()
            .url(url)
            .delete()
            .build()

        client.newCall(request).enqueue(callback("DELETE"))
    }

    private fun callback(type: String) = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("$type Request", "Failed: ${e.message}")
            runOnUiThread {
                textView.text = "$type Failed: ${e.message}"
            }
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            Log.d("$type Request", "Code: ${response.code}, Body: $body")

            runOnUiThread {
                textView.text = if (response.isSuccessful) {
                    "$type Success!\nHTTP ${response.code}\nResponse: $body"
                } else {
                    "$type Failed\nHTTP ${response.code}\nResponse: $body"
                }
            }
        }
    }
}