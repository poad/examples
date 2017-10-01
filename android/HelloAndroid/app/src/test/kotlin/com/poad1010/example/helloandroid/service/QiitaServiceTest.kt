package com.poad1010.example.helloandroid.service

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers

class QiitaServiceTest {

    @Test
    fun test() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://qiita.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val qiita = retrofit.create(QiitaService::class.java)

        qiita.tags()
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        {
                            assertTrue(it.isNotEmpty())

                        }
                )
    }
}