package br.com.exercicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.exercicios.repository.Repository

class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}