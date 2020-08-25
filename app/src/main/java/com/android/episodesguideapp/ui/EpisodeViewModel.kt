package com.android.episodesguideapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.episodesguideapp.EpisodesApplication
import com.android.episodesguideapp.models.Episode
import com.android.episodesguideapp.models.EpisodeResponse
import com.android.episodesguideapp.repository.EpisodeRepository
import com.android.episodesguideapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class EpisodeViewModel(
    app: Application,
    val episodeRepository: EpisodeRepository
) : AndroidViewModel(app) {

    val episodes: MutableLiveData<Resource<EpisodeResponse>> = MutableLiveData()

    init {
        getEpisodes()
    }

    fun getEpisodes() = viewModelScope.launch {
        safeEpisodesCall()

    }

    private fun handleEpisodeResponse(response: Response<EpisodeResponse>): Resource<EpisodeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveEpisode(episode: Episode) = viewModelScope.launch {
        episodeRepository.insert(episode)
    }

    fun getSavedEpisodes() = episodeRepository.getSavedEpisodes()

    fun deleteEpisodes(episode: Episode) = viewModelScope.launch {
        episodeRepository.deleteEpisode(episode)
    }

    private suspend fun safeEpisodesCall(){
        episodes.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = episodeRepository.getEpisodes()
                episodes.postValue(handleEpisodeResponse(response))
            } else {
                episodes.postValue(Resource.Error("no internet"))
            }
        } catch (t: Throwable){
            when(t){
                is IOException -> episodes.postValue(Resource.Error("network failure"))
                else -> episodes.postValue(Resource.Error("conversion error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<EpisodesApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false

            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}