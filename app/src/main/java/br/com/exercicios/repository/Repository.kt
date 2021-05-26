package br.com.exercicios.repository

import br.com.exercicios.api.InstanceAPI
import br.com.exercicios.model.Cep
import retrofit2.Response

class Repository {
    suspend fun getCep(cep: String) : Response<Cep> {
        return InstanceAPI.api.getCep(cep)
    }
}