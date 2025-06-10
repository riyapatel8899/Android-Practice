package com.example.retrofit

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofit.model.Comment
import com.example.retrofit.model.JsonPlaceHolderApi
import com.example.retrofit.model.Post
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
    private lateinit var textViewResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewResult = findViewById(R.id.text_view_result)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

//        getPosts()
//        getComments()
//        createPost()
//        updatePost()
        deletePost()
    }

    private  fun getPosts() {

        val parameters = mutableMapOf<String, String>()
        parameters["userId"] = "1"
        parameters["_sort"] = "id"
        parameters["_order"] = "desc"

        val call = jsonPlaceHolderApi.getPosts(parameters)

        call.enqueue(object: Callback<MutableList<Post>> {
            override fun onResponse(call: Call<MutableList<Post>>, response: Response<MutableList<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null) {
                        for (post in posts) {
                            var content = ""
                            content += "ID: " + "${post.id}" + "\n"
                            content += "User ID: " + "${post.userId}" + "\n"
                            content += "Title: " + post.title.toString() + "\n"
                            content += "Text: " + post.text.toString() + "\n\n"

                            textViewResult.append(content)
                        }
                    }
                }
                else {
                    textViewResult.text = response.code().toString()
                }
            }

            override fun onFailure(call: Call<MutableList<Post>>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getComments() {
        val call =  jsonPlaceHolderApi.getComments("https://jsonplaceholder.typicode.com/posts/5/comments")

        call.enqueue(object : Callback<MutableList<Comment>> {
            override fun onResponse(
                call: Call<MutableList<Comment>>,
                response: Response<MutableList<Comment>>
            ) {
                if (response.isSuccessful) {
                    val comments = response.body()

                    if (comments != null) {
                        for (comment in comments) {
                            var content = ""
                            content += "ID: " + "${comment.id}" + "\n"
                            content += "Post ID: " + "${comment.postId}" + "\n"
                            content += "Name: " + comment.name.toString() + "\n"
                            content += "Email: " + comment.email.toString() + "\n"
                            content += "Text: " + comment.text.toString() + "\n\n"

                            textViewResult.append(content)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Comment>>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun createPost() {
        val post = Post(20, "New title", "New Text")

        val fields  = mutableMapOf<String, String>()
        fields["userId"] = "66"
        fields["title"] = "This is new title"
        fields["text"] = "This is new text"
        val call = jsonPlaceHolderApi.createPost(fields)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(
                call: Call<Post>,
                response: Response<Post>
            ) {
                if (response.isSuccessful) {
                    val postResponse = response.body()
                    if (postResponse != null) {
                        var content = ""
                        content += "Code: " + response.code() + "\n"
                        content += "ID: " + "${postResponse.id}" + "\n"
                        content += "User ID: " + "${postResponse.userId}" + "\n"
                        content += "Title: " + postResponse.title + "\n"
                        content += "Text: " + postResponse.text + "\n\n"

                        textViewResult.text = content
                    } else {
                        textViewResult.text = response.code().toString()
                    }
                }
            }

            override fun onFailure(call: Call<Post>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updatePost() {
        val post = Post(25, null, "How are you?")

        val call = jsonPlaceHolderApi.patchPost(5, post)

        call.enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful){
                    val postResponse = response.body()
                    if (postResponse != null) {
                        var content = ""
                        content += "Code: " + response.code() + "\n"
                        content += "ID: " + "${postResponse.id}" + "\n"
                        content += "User ID: " + "${postResponse.userId}" + "\n"
                        content += "Title: " + postResponse.title + "\n"
                        content += "Text: " + postResponse.text + "\n\n"

                        textViewResult.text = content
                    } else {
                        textViewResult.text = response.code().toString()
                    }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deletePost() {
        val call = jsonPlaceHolderApi.deletePost(25)

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                 if (response.isSuccessful) {
                     textViewResult.text = response.code().toString()
                 }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}