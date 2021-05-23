package br.com.exercicios.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonWriter
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.exercicios.R
import br.com.exercicios.databinding.ActivityEx01Binding
import br.com.exercicios.databinding.CardNoteBinding
import br.com.exercicios.db.Database
import br.com.exercicios.model.Note

class Ex01Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEx01Binding
    private lateinit var database: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEx01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(this, Database::class.java, "AppDb").build()

        binding.fab.setOnClickListener() {
            startActivity(Intent(this, NewNoteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user -> {
                startActivity(Intent(this, UserActivity::class.java))
            }
            R.id.config -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun refreshNotes() {

        Thread {
            val notes = listNotes()
            runOnUiThread {
                updateUI(notes);
            }
        }.start()
    }

    fun listNotes(): List<Note> {
        val database = Room.databaseBuilder(this, Database::class.java, "AppDb").build()

        return database
            .noteDao()
            .list();
    }

    fun updateUI(notes: List<Note>) {
        binding.noteContainer.removeAllViews()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val color = prefs.getInt("noteColor", R.color.noteDefaultColor)

        notes.forEach {
            val cardBinding = CardNoteBinding.inflate(layoutInflater)
            cardBinding.txtTitle.text = it.title
            cardBinding.txtDesc.text = it.desc
            cardBinding.txtUser.text = it.user
            cardBinding.btnRemove.setOnClickListener{ view ->
                Thread {
                    removeNote(it)
                    runOnUiThread {
                        Toast.makeText(this, "Nota removida!", Toast.LENGTH_LONG ).show()
                        refreshNotes();
                    }
                }.start()
            }

            cardBinding.btnEdit.setOnClickListener { view ->
                var intent = Intent(this, EditNoteActivity::class.java)
                intent.putExtra("note", it)
                startActivity(intent)
            }

            cardBinding.root.setCardBackgroundColor(color)

            binding.noteContainer.addView(cardBinding.root)
        }
    }

    private fun removeNote(note: Note) {
        val database = Room.databaseBuilder(this, Database::class.java, "AppDb").build()
        database.
            noteDao()
            .delete(note)
    }
}
