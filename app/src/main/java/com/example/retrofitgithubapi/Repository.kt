package com.example.retrofitgithubapi

data class Repository(
 val name: String,
 val description: String?,
 val html_url: String? = ""
)