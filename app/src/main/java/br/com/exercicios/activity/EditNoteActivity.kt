package br.com.exercicios.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.room.Room
import br.com.exercicios.R
import br.com.exercicios.databinding.ActivityEditNoteBinding
import br.com.exercicios.databinding.ActivityNewNoteBinding
import br.com.exercicios.db.Database
import br.com.exercicios.model.Note

class EditNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val note = intent.getSerializableExtra("note") as? Note

        note.let {
            binding.txtId.text = it?.id.toString()
            binding.etTitle.setText(it?.title)
            binding.etDesc.setText(it?.desc)
            binding.btnAdd.text = "Salvar alterações"
        }

        binding.btnAdd.setOnClickListener {

            val id = binding.txtId.text.toString()
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()

            when{
                TextUtils.isEmpty(binding.etTitle.text.toString().trim(){it <= ' '}) -> {
                    binding.etTitle.error = "O título é obrigatório!"
                    true
                }
                TextUtils.isEmpty(binding.etDesc.text.toString().trim(){it <= ' '}) -> {
                    binding.etDesc.error = "A descrição é obrigatória!"
                    true
                }
                else ->{
                    Thread {
                        val database = Room.databaseBuilder(this, Database::class.java, "AppDb").build()
                        database.
                        noteDao()
                            .update(id.toInt(), title, desc)

                        runOnUiThread {
                            Toast.makeText(this, "Nota atualizada!", Toast.LENGTH_LONG ).show()
                            finish()
                        }
                    }.start()
                }
            }
        }
    }
}