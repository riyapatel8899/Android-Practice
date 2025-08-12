package com.example.retro

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.retro.model.Post
import com.example.retro.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private val api = RetrofitClient.instance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

//        getPosts()
//        createPost()
        updatePost(1)
//        deletePost(1)

    }

    private fun getPosts() {
        api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val posts = response.body()
                if (posts != null) {
                    textView.append("GET:\n${posts[0]}\n\n")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("GET_ERROR", t.message ?: "Unknown error")
            }
        })
    }

    private fun createPost() {
        val newPost = Post(userId = 101, title = "New Title", body = "This is the body.")
        api.createPost(newPost).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                textView.append("POST:\n${response.body()}\n\n")
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("POST_ERROR", t.message ?: "Unknown error")
            }
        })
    }

    private fun updatePost(postId: Int) {
        val updatedPost = Post(userId = 1, id = postId, title = "Updated Title", body = "Updated Body")
        api.updatePost(postId, updatedPost).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                textView.append("PUT:\n${response.body()}\n\n")
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("PUT_ERROR", t.message ?: "Unknown error")
            }
        })
    }

    private fun deletePost(postId: Int) {
        api.deletePost(postId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                textView.append("DELETE: Post $postId deleted\n\n")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("DELETE_ERROR", t.message ?: "Unknown error")
            }
        })
    }
}