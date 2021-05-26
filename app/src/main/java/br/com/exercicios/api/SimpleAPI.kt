package br.com.exercicios.api

import br.com.exercicios.model.Cep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SimpleAPI {

    @GET("ws/{cepNumber}/json/")
    suspend fun getCep(
        @Path("cepNumber") cep: String
    ) : Response<Cep>
}