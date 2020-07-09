package com.poad1010.example.helloandroid

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.poad1010.example.helloandroid.adapter.TagListAdapter
import com.poad1010.example.helloandroid.service.QiitaService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listAdaptor = TagListAdapter(applicationContext)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://qiita.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        val qiita = retrofit.create(QiitaService::class.java)

        val listView: ListView = findViewById<ListView>(R.id.list_view)
        listView.adapter = listAdaptor

        try {
            qiita.tags()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                it.forEach({
                                    t -> println(t.id)
                                })
                                listAdaptor.tags = it
                                listAdaptor.notifyDataSetChanged()
                            }
                    )

        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}
