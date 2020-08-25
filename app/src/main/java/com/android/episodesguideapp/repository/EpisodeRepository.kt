package com.android.episodesguideapp.repository

import com.android.episodesguideapp.api.RetrofitInstance
import com.android.episodesguideapp.db.EpisodeDatabase
import com.android.episodesguideapp.models.Episode

class EpisodeRepository(
    val db: EpisodeDatabase
) {

    suspend fun getEpisodes() =
        RetrofitInstance.api.getAllEpisodes()

    suspend fun insert(episode: Episode) = db.getEpisodeDao().insert(episode)

    fun getSavedEpisodes() = db.getEpisodeDao().getAllEpisodes()

    suspend fun deleteEpisode(episode: Episode) = db.getEpisodeDao().deleteEpisode(episode)
}