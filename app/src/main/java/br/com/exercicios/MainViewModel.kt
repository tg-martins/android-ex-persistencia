package br.com.exercicios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.exercicios.model.Cep
import br.com.exercicios.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val buscaCepResponse: MutableLiveData<Response<Cep>> = MutableLiveData()

    fun getCep(cep: String) {
        viewModelScope.launch {
            val response = repository.getCep(cep)
            buscaCepResponse.value = response
        }
    }
}