package br.com.exercicios.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.exercicios.MainViewModel
import br.com.exercicios.MainViewModelFactory
import br.com.exercicios.R
import br.com.exercicios.databinding.ActivityEx01Binding
import br.com.exercicios.databinding.ActivityEx04Binding
import br.com.exercicios.repository.Repository

class Ex04Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEx04Binding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEx04Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.searchButton.setOnClickListener {
            buscarCep()
        }
    }

    private fun buscarCep() {
        if (binding.inputCep.unMasked.length >= 8) {
            viewModel.getCep(binding.inputCep.unMasked)
            viewModel.buscaCepResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    Log.d("Main", response.toString())
                    binding.constraintLabels.visibility = View.VISIBLE
                    binding.cepLabel.text = "CEP: ${response.body()!!.cep}"
                    binding.logradouroLabel.text = "Logradouro: ${response.body()!!.logradouro}"
                    binding.complementoLabel.text = "Complemento: ${response.body()!!.complemento}"
                    binding.bairroLabel.text = "Bairro: ${response.body()!!.bairro}"
                    binding.localidadeLabel.text = "Localidade: ${response.body()!!.localidade}"
                    binding.ufLabel.text = "UF: ${response.body()!!.uf}"
                    binding.ibgeLabel.text = "IBGE: ${response.body()!!.ibge}"
                    binding.giaLabel.text = "GIA: ${response.body()!!.gia}"
                    binding.dddLabel.text = "DDD: ${response.body()!!.ddd}"
                    binding.siafiLabel.text = "SIAFI: ${response.body()!!.siafi}"
                } else {
                    binding.constraintLabels.visibility = View.INVISIBLE
                    Log.d("Main", response.toString())
                    Toast.makeText(this, "Oops, o CEP informado não existe", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "Oops, o CEP informado é inválido", Toast.LENGTH_LONG).show()
        }
    }

}
