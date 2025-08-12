package com.example.advanceretro.repository

import com.example.advanceretro.model.Post
import com.example.advanceretro.api.JsonPlaceholderApi
import okhttp3.ResponseBody
import retrofit2.Call

class PostRepository(private val api: JsonPlaceholderApi) {

    fun getAll(): Call<List<Post>> = api.getPosts()

    fun create(post: Post): Call<Post> = api.createPost(post)

    fun update(id: Int, post: Post): Call<Post> = api.updatePost(id, post)

    fun delete(id: Int): Call<ResponseBody> = api.deletePost(id)

    //WITH Coroutine version (commented for reference only):
    /*
    suspend fun getAllSuspend(): List<Post> = api.getPostsSuspend()
    suspend fun createSuspend(post: Post): Post = api.createPostSuspend(post)
    */

}