package com.example.rafaelanastacioalves.moby.repository.http;


import com.example.rafaelanastacioalves.moby.domain.entities.New
import retrofit2.http.GET
import retrofit2.http.Path

interface APIClient {

    @GET("v0/topstories.json")
    suspend fun getNewsList(): List<Long>;

    @GET("v0/item/{newId}.json")
    suspend fun getNewsDetails(@Path("newId") id: Long): New

}
