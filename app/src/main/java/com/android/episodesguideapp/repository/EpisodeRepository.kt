package com.android.episodesguideapp.repository

import com.android.episodesguideapp.api.RetrofitInstance
import com.android.episodesguideapp.db.EpisodeDatabase
import retrofit2.Retrofit

class EpisodeRepository(
    val db: EpisodeDatabase
) {

    suspend fun getEpisodes() =
        RetrofitInstance.api.getAllEpisodes()
}