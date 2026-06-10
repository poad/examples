package com.poad1010.example.helloandroid.service

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class QiitaServiceTest {

  @Test
  fun test() {
    val retrofit = Retrofit.Builder()
      .baseUrl("https://qiita.com")
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val service = retrofit.create(QiitaService::class.java)

    assertTrue(service != null)
  }
}

