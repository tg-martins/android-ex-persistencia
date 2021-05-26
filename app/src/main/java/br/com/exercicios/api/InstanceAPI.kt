package br.com.exercicios.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanceAPI {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://viacep.com.br")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleAPI by lazy {
        retrofit.create(SimpleAPI::class.java)
    }
}