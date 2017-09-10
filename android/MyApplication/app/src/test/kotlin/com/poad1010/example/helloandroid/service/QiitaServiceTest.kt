package com.poad1010.example.helloandroid.service

import org.junit.jupiter.api.Assertions.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QiitaServiceTest {

    @Test
    fun test() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://qiita.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val qiita = retrofit.create(QiitaService::class.java)
        val res = qiita.tags().execute()

        assertTrue(res.isSuccessful)
        assertFalse(res.body().orEmpty().isEmpty())

        res.body().orEmpty().map({it.id}).forEach(System.out::println)
    }
}