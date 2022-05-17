package com.example.internproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_news.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException

class NewsActivity : AppCompatActivity() {
    private lateinit var newsArray : ArrayList<News>
    private lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsArray = ArrayList()
        supportActionBar!!.hide()
        newsRecycler.layoutManager = LinearLayoutManager(this)
        makeRequest()
        adapter = NewsAdapter()

        newsRecycler.adapter=adapter



    }
    fun makeRequest(){
        val Url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=7b6541d01c664fe99a396931de736e24"
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(Url).build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("No","Not Done")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val res = response.body!!.string()
                runOnUiThread {
                    val jObject = JSONObject(res)
                    val jsonArray = jObject.getJSONArray("articles")
                    for(i in 0 until jsonArray.length()){
                        val newsObject = jsonArray.getJSONObject(i)
                        val news = News(
                            newsObject.getString("title"),
                            newsObject.getString("content"),
                            newsObject.getString("author"),
                            newsObject.getString("url"),
                            newsObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }
                    adapter.updateNews(newsArray)

                }

            }

        })

    }
}