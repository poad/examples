package com.poad1010.example.helloandroid.service

import com.poad1010.example.helloandroid.model.Tag
import retrofit2.http.GET
import rx.Observable

interface QiitaService {
    @GET("/api/v2/tags")
    fun tags(): Observable<List<Tag>>
}