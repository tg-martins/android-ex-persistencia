package br.com.exercicios.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.exercicios.model.Note

@Database(entities = [Note::class], version = 2)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): NoteDao;
}
