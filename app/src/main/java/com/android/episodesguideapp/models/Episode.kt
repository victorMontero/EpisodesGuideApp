package com.android.episodesguideapp.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "episodes"
)
data class Episode(
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Image,
    val name: String,
    val number: Int,
    val runtime: Int,
    val season: Int,
    val summary: String,
    val url: String
) : Serializable