package com.android.episodesguideapp.ui.fragments

import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.episodesguideapp.R
import com.android.episodesguideapp.models.Episode
import com.android.episodesguideapp.ui.EpisodeViewModel
import com.android.episodesguideapp.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_episodes.*
import kotlinx.android.synthetic.main.item_episode_preview.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var viewModel: EpisodeViewModel
    val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val episode = args.episode
        web_view_details_fragment.apply {
            webViewClient = WebViewClient()
            loadUrl(episode.url)
        }

        fab_to_favorite_episodes.setOnClickListener {
            viewModel.saveEpisode(episode)
            Snackbar.make(view, "episode saved by ricky", Snackbar.LENGTH_SHORT).show()
        }
    }




}