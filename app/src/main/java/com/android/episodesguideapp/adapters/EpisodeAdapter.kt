package com.android.episodesguideapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.episodesguideapp.R
import com.android.episodesguideapp.models.Episode
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_episode_preview.view.*

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Episode>() {

        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_episode_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(episode.image.original).into(item_image_episode)
            item_episode_name.text = episode.name
            item_season.text = episode.season.toString()
            item_airdate.text = episode.airdate

            setOnClickListener {
                onItemClickListener?.let { it(episode) }
            }
        }
    }

    private var onItemClickListener: ((Episode) -> Unit)? = null

    fun setOnItemClickListener(listener: (Episode) -> Unit) {
        onItemClickListener = listener
    }


}