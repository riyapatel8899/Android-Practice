package com.example.advanceretro

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.advanceretro.model.Post
import com.example.advanceretro.network.RetrofitClient
import com.example.advanceretro.repository.PostRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var repository: PostRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tokenProvider = { "dummy_token_123" }
        val api = RetrofitClient.create(tokenProvider)
        repository = PostRepository(api)

        fetchPosts()
        createPost()
        updatePost(1)
        deletePost(1)
    }

    private fun fetchPosts() {
        repository.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body().orEmpty()
                    Log.d("MainActivity", "✅ Posts fetched: ${posts.size}")
                } else {
                    Log.e("MainActivity", "❌ Error Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("MainActivity", "❌ Failure: ${t.message}")
            }
        })
    }

    private fun createPost() {
        val post = Post(userId = 1, title = "New Post", body = "This is a new post.")
        repository.create(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "✅ Post created: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("MainActivity", "❌ Create failed: ${t.message}")
            }
        })
    }

    private fun updatePost(id: Int) {
        val post = Post(userId = 1, id = id, title = "Updated Title", body = "Updated Content")
        repository.update(id, post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "✅ Post updated: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("MainActivity", "❌ Update failed: ${t.message}")
            }
        })
    }

    private fun deletePost(id: Int) {
        repository.delete(id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "✅ Post deleted with ID: $id")
                } else {
                    Log.e("MainActivity", "❌ Delete failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("MainActivity", "❌ Delete error: ${t.localizedMessage}")
            }
        })
    }

    /*
    //  WITH Coroutine version (example only, not used in real code):
    fun coroutineExample() = runBlocking {
        try {
            val posts = repository.getAllSuspend()
            println("Posts: ${posts.size}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
    */

}
