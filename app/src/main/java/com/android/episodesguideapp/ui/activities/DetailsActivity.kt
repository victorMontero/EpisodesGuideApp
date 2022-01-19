package com.android.episodesguideapp.ui.activities

import com.android.episodesguideapp.models.Episode
import com.android.episodesguideapp.ui.FlutterRouter
import com.android.episodesguideapp.ui.flutter.model.NetworkMethodChannelData
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class DetailsActivity : FlutterActivity() {

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val methodChannelData = NetworkMethodChannelData()

        intent.extras?.getInt(FlutterRouter.MethodChannelParams.DETAILS_ARG)?.let { episode ->
            methodChannelData.episode = episode as Episode
        }
    }
}