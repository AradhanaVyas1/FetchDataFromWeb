package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Quote (
    val id : Int,
    val quote: String,
    val author: String
)

data class QuoteResponse (
    val quotes : List<Quote>
)

interface QuoteService {
    @GET("quotes")
    suspend fun getQuotes(): QuoteResponse

    companion object {
        fun create(): QuoteService {
            return Retrofit.Builder().baseUrl("https://dummyjson.com/").addConverterFactory(
                GsonConverterFactory.create()).build().create(QuoteService :: class.java)
        }
    }
}