package com.application.naumenko.presentation.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.application.naumenko.R
import com.application.naumenko.databinding.ViewHolderFilmListBinding
import com.application.naumenko.domain.model.About
import com.application.naumenko.presentation.utils.savePictureToFile
import java.io.File

class AdapterFavorites :
    ListAdapter<About, AdapterFavorites.ItemViewHolder>(DiffCallback()) {

    private lateinit var listener: ItemClickListener
    fun setItemListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ViewHolderFilmListBinding.inflate(LayoutInflater.from(parent.context)), parent.context
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

    class ItemViewHolder(
        private val binding: ViewHolderFilmListBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: About, listener: ItemClickListener) {
            with(binding) {
                filmListFilmTitle.text = item.nameRu
                filmListSmallPoster.setImageURI(
                    File(
                        context.filesDir,
                        context.resources.getString(R.string.fileName, item.kinopoiskId)
                    ).toUri()
                )
                filmListFilmGenre.text = item.genres[0].genre.replaceFirstChar { it.uppercase() }
                filmListFilmYear.text = item.year

                filmCardList.setOnClickListener {
                    listener.onItemClick(item.kinopoiskId)
                }
                filmListFavoritesIcon.visibility = View.VISIBLE

                filmCardList.setOnLongClickListener {
                    if (item.isFavorite) {
                        File(
                            context.filesDir,
                            context.resources.getString(R.string.fileName, item.kinopoiskId)
                        ).delete()
                        listener.onItemRemoveClick(item.kinopoiskId)
                        filmListFavoritesIcon.visibility = View.INVISIBLE
                        item.isFavorite = false
                    } else {
                        savePictureToFile(
                            context.resources.getString(R.string.fileName, item.kinopoiskId),
                            item.posterUrl,
                            context
                        )
                        listener.onItemClick(item.kinopoiskId)
                        filmListFavoritesIcon.visibility = View.VISIBLE
                        item.isFavorite = true
                    }
                    true
                }
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<About>() {
        override fun areItemsTheSame(oldItem: About, newItem: About): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: About, newItem: About): Boolean {
            return oldItem == newItem
        }
    }
}
