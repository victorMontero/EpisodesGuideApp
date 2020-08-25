package com.android.episodesguideapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.episodesguideapp.R
import com.android.episodesguideapp.adapters.EpisodeAdapter
import com.android.episodesguideapp.ui.EpisodeViewModel
import com.android.episodesguideapp.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
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

        val itemTouchHelper = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val episode = episodeAdapter.differ.currentList[position]
                viewModel.deleteEpisodes(episode)
                Snackbar.make(view, "great morty", Snackbar.LENGTH_LONG).apply {
                    setAction("undo"){
                        viewModel.saveEpisode(episode)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(recycler_view_favorites_fragment)
        }

        viewModel.getSavedEpisodes().observe(viewLifecycleOwner, Observer {episodes ->
            episodeAdapter.differ.submitList(episodes)

        })
    }

    private fun setupRecyclerView(){
        episodeAdapter = EpisodeAdapter()
        recycler_view_favorites_fragment.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}