package com.android.episodesguideapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.episodesguideapp.R
import com.android.episodesguideapp.adapters.EpisodeAdapter
import com.android.episodesguideapp.ui.EpisodeViewModel
import com.android.episodesguideapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_episodes.*
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    lateinit var viewModel: EpisodeViewModel
    lateinit var episodeAdapter: EpisodeAdapter

    val TAG = "FavoritesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        episodeAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("episode", it)
            }
            findNavController().navigate(
                R.id.action_favoritesFragment_to_detailsFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView(){
        episodeAdapter = EpisodeAdapter()
        recycler_view_favorites_fragment.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}