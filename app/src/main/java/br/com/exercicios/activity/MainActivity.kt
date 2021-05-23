package br.com.exercicios.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.exercicios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnEx1.setOnClickListener {
            startActivity(Intent(this, Ex01Activity::class.java))
        }

        binding.btnEx4.setOnClickListener {
            startActivity(Intent(this, Ex04Activity::class.java))
        }
    }
}