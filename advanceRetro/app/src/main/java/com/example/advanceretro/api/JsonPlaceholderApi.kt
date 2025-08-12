package com.example.advanceretro.api

import com.example.advanceretro.model.Post
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.Call


interface JsonPlaceholderApi {

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @POST("posts")
    fun createPost(@Body post: Post): Call<Post>

    @PUT("posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: Post): Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<ResponseBody>
}


    //WITH Coroutine version (commented for reference only):
    /*
    @GET("posts")
    suspend fun getPostsSuspend(): List<Post>

    @POST("posts")
    suspend fun createPostSuspend(@Body post: Post): Post
    */
