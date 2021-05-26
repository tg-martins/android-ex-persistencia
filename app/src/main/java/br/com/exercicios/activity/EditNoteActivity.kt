package br.com.exercicios.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import br.com.exercicios.R
import br.com.exercicios.databinding.ActivityEditNoteBinding
import br.com.exercicios.databinding.ActivityNewNoteBinding
import br.com.exercicios.db.Database
import br.com.exercicios.model.Note
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class EditNoteActivity : AppCompatActivity(), ColorPickerDialogListener {

    lateinit var binding: ActivityNewNoteBinding
    var colorBackground: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val note = intent.getSerializableExtra("note") as? Note

        note.let {
            binding.txtId.text = it?.id.toString()
            binding.etTitle.setText(it?.title)
            binding.etDesc.setText(it?.desc)
            colorBackground = it!!.colorBackground
            binding.colorView.setBackgroundColor(it!!.colorBackground)
            binding.btnAdd.text = "Salvar alterações"
        }

        binding.buttonSelectColor.setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.newBuilder()
                .setDialogTitle(R.string.selectColorLabel)
                .setSelectedButtonText(R.string.selectColorButton)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowPresets(false)
                .setColor(colorBackground)
                .setAllowCustom(false)
                .setShowAlphaSlider(false)
            colorPickerDialog.show(this)
        }

        binding.btnAdd.setOnClickListener {

            val id = binding.txtId.text.toString()
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()
            val colorCard = colorBackground

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
                            .update(id.toInt(), title, desc, colorCard)

                        runOnUiThread {
                            Toast.makeText(this, "Nota atualizada!", Toast.LENGTH_LONG ).show()
                            finish()
                        }
                    }.start()
                }
            }
        }
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        binding.colorView.setBackgroundColor(color)
        Log.d("EditNoteActivity", "onColorSelected() called with: dialogId = [" + dialogId + "], color = [" + color + "]");
        colorBackground = color
    }

    override fun onDialogDismissed(dialogId: Int) {
        Log.d("EditNoteActivity", "onDialogDismissed() called with: dialogId = [" + dialogId + "]");
    }
}