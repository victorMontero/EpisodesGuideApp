package com.android.episodesguideapp.ui

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.episodesguideapp.R
import com.android.episodesguideapp.db.EpisodeDatabase
import com.android.episodesguideapp.repository.EpisodeRepository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: EpisodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title =
            Html.fromHtml("<font color='#b7d485'>" + getText(R.string.app_name) + "</font>")


        val episodeRepository = EpisodeRepository(EpisodeDatabase(this))
        val viewModelProviderFactory = EpisodeViewModelProviderFactory(application,
            episodeRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(EpisodeViewModel::class.java)

        bottom_navigation_view.setupWithNavController(episodes_nav_host_fragment.findNavController())
    }
}