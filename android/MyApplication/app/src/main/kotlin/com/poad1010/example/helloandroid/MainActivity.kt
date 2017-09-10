package com.poad1010.example.helloandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.poad1010.example.helloandroid.adapter.TagListAdapter
import com.poad1010.example.helloandroid.service.QiitaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listAdaptor = TagListAdapter(applicationContext)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://qiita.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val qiita = retrofit.create(QiitaService::class.java)

        val listView: ListView = findViewById(R.id.list_view) as ListView
        listView.adapter = listAdaptor

//        val res = qiita.tags().execute()
//        res.body()?.let { listAdaptor.tags = it}

//        listAdaptor.notifyDataSetChanged()
    }
}
