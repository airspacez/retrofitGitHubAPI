package com.example.retrofitgithubapi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gitHubApi = retrofit.create(GitHubApi::class.java)

        gitHubApi.getRepositories("dartPelmen")?.enqueue(object : Callback<List<Repository?>?> {
            override fun onResponse(
                call: Call<List<Repository?>?>,
                response: Response<List<Repository?>?>
            ) {
                val listView = findViewById<ListView>(R.id.list_view)
                val repositories: List<Repository>? = response.body() as List<Repository>?
                repositories?.let {
                    val repositoryNames = repositories.map { it.name }
                    val adapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_list_item_1,
                        repositoryNames
                    )


                    listView.adapter = adapter

                    listView.setOnItemClickListener { _, _, position, _ ->
                        val repository = repositories[position]
                        openRepositoryUrl(repository.html_url, this@MainActivity)
                    }
                }
                if (repositories != null) {
                    Log.d("SIZE!", repositories.size.toString())
                };
            }

            override fun onFailure(call: Call<List<Repository?>?>, t: Throwable) {

            }
        })
    }

    private fun openRepositoryUrl(url: String?, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}