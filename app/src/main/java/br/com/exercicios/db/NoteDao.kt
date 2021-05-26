package br.com.exercicios.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.exercicios.model.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Query(value = "SELECT * FROM Note")
    fun list(): List<Note>

    @Query(value = "SELECT * FROM Note WHERE title LIKE :title")
    fun findByTitle(title: String): List<Note>

    @Delete
    fun delete(note: Note)


    @Query("UPDATE Note SET title=:title, `desc`=:description, colorBackground=:colorBackground WHERE id = :id")
    fun update(id: Int, title: String, description: String, colorBackground: Int)
}