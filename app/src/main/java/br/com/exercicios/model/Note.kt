package br.com.exercicios.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var desc: String,
    val user: String,
    val colorBackground: Int
) : Serializable