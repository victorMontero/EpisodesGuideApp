package com.android.episodesguideapp.api

import com.android.episodesguideapp.models.EpisodeResponse
import retrofit2.Response
import retrofit2.http.GET

interface EpisodesAPI {

    @GET("shows/216/episodes")
    suspend fun getAllEpisodes(): Response<EpisodeResponse>
}