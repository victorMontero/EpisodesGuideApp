package com.android.episodesguideapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.episodesguideapp.R
import com.android.episodesguideapp.adapters.EpisodeAdapter
import com.android.episodesguideapp.ui.EpisodeViewModel
import com.android.episodesguideapp.ui.MainActivity
import com.android.episodesguideapp.util.Resource
import kotlinx.android.synthetic.main.fragment_episodes.*

class EpisodesFragment : Fragment(R.layout.fragment_episodes) {

    lateinit var viewModel: EpisodeViewModel
    lateinit var episodeAdapter: EpisodeAdapter

    val TAG = "EpisodeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        episodeAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("episode", it)
            }
            findNavController().navigate(
                R.id.action_episodesFragment_to_detailsFragment,
                bundle
            )
        }

        viewModel.episodes.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { episodeResponse ->
                        episodeAdapter.differ.submitList(episodeResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "an error occoured: $message", Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progress_bar_fragment_episodes.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progress_bar_fragment_episodes.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        episodeAdapter = EpisodeAdapter()
        recycler_view_episodes_fragment.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}