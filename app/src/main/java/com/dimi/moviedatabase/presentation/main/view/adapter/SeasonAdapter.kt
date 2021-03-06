package com.dimi.moviedatabase.presentation.main.view.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.dimi.moviedatabase.business.domain.model.Season
import com.dimi.moviedatabase.databinding.LayoutSeasonListItemBinding
import com.dimi.moviedatabase.framework.network.NetworkConstants.SMALL_IMAGE_URL_PREFIX

class SeasonAdapter(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _diffCallback = object : DiffUtil.ItemCallback<Season>() {

        override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, _diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return SeasonViewHolder(
            LayoutSeasonListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SeasonViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<Season>
    ) {
        for (season in list) {
            if (season.posterPath != null)
                requestManager
                    .load(SMALL_IMAGE_URL_PREFIX + season.posterPath)
                    .preload()
        }
    }

    fun submitList(list: List<Season>?) {
        differ.submitList(list)
    }

    class SeasonViewHolder
    constructor(
        private val binding: LayoutSeasonListItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Season)  {
            binding.season = item
            binding.arrowSelectSeason.setOnClickListener {
                interaction?.onItemSelected(absoluteAdapterPosition, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Season)
    }
}