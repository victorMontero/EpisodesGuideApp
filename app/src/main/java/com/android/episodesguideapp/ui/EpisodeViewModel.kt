package com.android.episodesguideapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.episodesguideapp.models.EpisodeResponse
import com.android.episodesguideapp.repository.EpisodeRepository
import com.android.episodesguideapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class EpisodeViewModel(
    val episodeRepository: EpisodeRepository
) : ViewModel() {

    val episodes: MutableLiveData<Resource<EpisodeResponse>> = MutableLiveData()

    init {
        getEpisodes()
    }

    fun getEpisodes() = viewModelScope.launch {
        episodes.postValue(Resource.Loading())
        val response = episodeRepository.getEpisodes()
        episodes.postValue(handleEpisodeResponse(response))

    }

    private fun handleEpisodeResponse(response: Response<EpisodeResponse>): Resource<EpisodeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}