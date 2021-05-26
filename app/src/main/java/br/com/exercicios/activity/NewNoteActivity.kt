package br.com.exercicios.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.room.Room
import br.com.exercicios.R
import br.com.exercicios.databinding.ActivityNewNoteBinding
import br.com.exercicios.db.Database
import br.com.exercicios.model.Note
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener


class NewNoteActivity : AppCompatActivity(), ColorPickerDialogListener {
    lateinit var binding: ActivityNewNoteBinding
    var colorBackground: Int = 0

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
                        user = sharedPrefs.getString("user", "") as String,
                        colorBackground = colorBackground
                    )
                    Thread {
                        saveNote(note)
                        finish()
                    }.start()
                }
            }
        }

        binding.buttonSelectColor.setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.newBuilder()
                .setDialogTitle(R.string.selectColorLabel)
                .setSelectedButtonText(R.string.selectColorButton)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowPresets(false)
                .setColor(if (colorBackground == 0) Color.BLACK else colorBackground)
                .setAllowCustom(false)
                .setShowAlphaSlider(false)
            colorPickerDialog.show(this)
        }
    }

    fun saveNote(note: Note) {
        val database = Room.databaseBuilder(this, Database::class.java, "AppDb").fallbackToDestructiveMigration().build()

        database
            .noteDao()
            .insert(note)
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        binding.colorView.setBackgroundColor(color)
        Log.d("NewNoteActivity", "onColorSelected() called with: dialogId = [" + dialogId + "], color = [" + color + "]");
        colorBackground = color
    }

    override fun onDialogDismissed(dialogId: Int) {
        Log.d("NewNoteActivity", "onDialogDismissed() called with: dialogId = [" + dialogId + "]");
    }
}

