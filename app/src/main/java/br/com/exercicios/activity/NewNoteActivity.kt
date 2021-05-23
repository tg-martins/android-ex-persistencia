package br.com.exercicios.activity

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.exercicios.databinding.ActivityNewNoteBinding
import br.com.exercicios.db.Database
import br.com.exercicios.model.Note


class NewNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener() {

            when {
                TextUtils.isEmpty(binding.etTitle.text.toString().trim(){it <= ' '}) -> {
                    binding.etTitle.error = "O título é obrigatório!"
                    true
                }
                TextUtils.isEmpty(binding.etDesc.text.toString().trim(){it <= ' '}) -> {
                    binding.etDesc.error = "A descrição é obrigatória!"
                    true
                }
                else -> {
                    val sharedPrefs = getSharedPreferences("Users", Context.MODE_PRIVATE)
                    val note = Note(
                        title = binding.etTitle.text.toString(),
                        desc = binding.etDesc.text.toString(),
                        user = sharedPrefs.getString("user", "") as String
                    )
                    Thread {
                        saveNote(note)
                        finish()
                    }.start()
                }
            }
        }
    }

    fun saveNote(note: Note) {
        val database = Room.databaseBuilder(this, Database::class.java, "AppDb").build()

        database
            .noteDao()
            .insert(note)
    }
}

