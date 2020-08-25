package com.android.episodesguideapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.episodesguideapp.repository.EpisodeRepository

class EpisodeViewModelProviderFactory(val app: Application, val episodeRepository : EpisodeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EpisodeViewModel(app, episodeRepository) as T
    }
}