package com.example.retrofitgithubapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubApi {
    @GET("users/{username}/repos")
    fun getRepositories(@Path("username") username: String?): Call<List<Repository?>?>?
}