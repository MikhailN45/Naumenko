package com.application.naumenko.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.application.naumenko.R
import com.application.naumenko.databinding.ViewHolderFilmListBinding
import com.application.naumenko.presentation.fragments.FragmentFilmDetails
import com.application.naumenko.domain.model.Film
import com.application.naumenko.presentation.utils.savePictureToFile
import java.io.File
import java.util.Locale

class AdapterFilmList :
    ListAdapter<Film, AdapterFilmList.ItemViewHolder>(DiffCallback()) {

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
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Film, listener: ItemClickListener) {
            with(binding) {
                filmListSmallPoster.load(
                    item.posterUrl.toUri().buildUpon().scheme("https").build()
                )
                filmListFilmTitle.text = item.nameRu
                filmListFilmYear.text = item.year
                filmListFilmGenre.text =
                    item.genres.map { it.genre }.joinToString(separator = " , ")
                        .replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }
                filmListFavoritesIcon.visibility =
                    if (item.isFavorite) View.VISIBLE else View.INVISIBLE

                filmCardList.setOnClickListener { view ->
                    view.findNavController().navigate(
                        R.id.action_filmListFragment_to_filmDetailsFragment,
                        FragmentFilmDetails.createArguments(id = item.filmId)
                    )
                }
                filmCardList.setOnLongClickListener {
                    val favorite = filmListFavoritesIcon.visibility == View.VISIBLE
                    if (favorite) {
                        File(
                            context.filesDir,
                            context.resources.getString(R.string.fileName, item.filmId)
                        ).delete()
                        listener.onItemRemoveClick(item.filmId)
                        filmListFavoritesIcon.visibility = View.INVISIBLE
                    } else {
                        savePictureToFile(
                            context.resources.getString(R.string.fileName, item.filmId),
                            item.posterUrl,
                            context
                        )
                        listener.onItemClick(item.filmId)
                        filmListFavoritesIcon.visibility = View.VISIBLE
                    }
                    true
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.nameRu == newItem.nameRu
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }
}