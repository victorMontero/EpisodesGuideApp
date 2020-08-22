package com.android.episodesguideapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.episodesguideapp.models.Episode

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: Episode): Long

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes(): LiveData<List<Episode>>

    @Delete
    suspend fun deleteEpisode(episode: Episode)
}