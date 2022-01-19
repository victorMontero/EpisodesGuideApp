package com.android.episodesguideapp.ui

import android.content.Intent
import com.android.episodesguideapp.models.Episode
import io.flutter.embedding.android.FlutterActivity

class FlutterRouter {

    companion object{
        const val FLUTTER_DETAILS_PAGE = "episodes/details"
    }

    fun openFlutterScreen(episode: Episode){
        //val intent: Intent = FlutterActivity.NewEngineIntentBuilder
    }

    object MethodChannelParams{
        const val DETAILS_ARG = "details"
    }


}