package com.example.internproject

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_news.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NewsActivity : AppCompatActivity(), NewsItemInterface, onCounrtryClick {
    private lateinit var newsArray : ArrayList<News>
    private lateinit var adapter: NewsAdapter
    private lateinit var adapterCountry: CountryAdapter
    private lateinit var id:String
    private lateinit var viewModel: noteViewModel
    private lateinit var encryptPassword: encryptPassword
    private lateinit var currentDate: String
    private lateinit var hm: HashMap<String,String>;

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        id = intent.getStringExtra("userid").toString()
        hm = HashMap();
        hm.put("India","in")
        hm.put("China","ch")
        hm.put("UK","eg")
        hm.put("japan","jp")
        hm.put("Usa","us")
        hm.put("Russia","rs")
        val sdf = SimpleDateFormat("dd/M/yyyy")
        currentDate = sdf.format(Date())
        encryptPassword = encryptPassword()
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(noteViewModel::class.java)
        newsArray = ArrayList()
        supportActionBar!!.hide()
        val layoutManagerCountry = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        countryRecyclerView.layoutManager = layoutManagerCountry

        adapterCountry = CountryAdapter(this)
        adapterCountry.update(hm)
        countryRecyclerView.adapter=adapterCountry
        val layoutManager  = LinearLayoutManager(this)
        newsRecycler.layoutManager = layoutManager
        makeRequest("in")
        adapter = NewsAdapter(this)

        newsRecycler.adapter=adapter
       newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
           override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               super.onScrolled(recyclerView, dx, dy)
               val visibleItemCount = layoutManager.childCount
               val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
               val total = adapter.itemCount
               Log.d("Abhishek"," "+visibleItemCount+" "+pastVisibleItem+" "+total)
               if ((visibleItemCount + pastVisibleItem) >= total) {

               }

           }

           override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
               super.onScrollStateChanged(recyclerView, newState)
           }
       })



    }
    fun makeRequest(country:String){
        val Url = "https://newsapi.org/v2/top-headlines?country=$country&apiKey=7b6541d01c664fe99a396931de736e24&pageSize=20"
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
                    Log.d("abhishek"," "+jObject)
                    val jsonArray = jObject.getJSONArray("articles")
                    newsArray.clear()
                    for(i in 0 until jsonArray.length()){
                        val newsObject = jsonArray.getJSONObject(i)
                        val news = News(
                            newsObject.getString("title"),
                            newsObject.getString("description"),
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

    override fun onItemClicked(item: News) {
        val  url = item.url;
        val  builder =  CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNoteMake(item: News) {
        val title = item.title;
        val content = item.content;

        val news=Note(currentDate,title,content,id.toInt(),false)
        viewModel.insertNote(news)
        finish()


    }

    override fun onClick(cn: String) {
        makeRequest(cn)
    }
}