package com.poad1010.example.helloandroid.service

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.schedulers.Schedulers

class QiitaServiceTest {

  @Test
  fun test() {
    val retrofit = Retrofit.Builder()
      .baseUrl("https://qiita.com")
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    retrofit.create(QiitaService::class.java).apply {
      val results = tags()
        .subscribeOn(Schedulers.newThread())
        .subscribe {
          assertTrue(it.isNotEmpty())
        }
      assertTrue(results.isDisposed)
    }
  }
}

