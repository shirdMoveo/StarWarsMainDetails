package com.starwarsmasterdetails.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starwarsmasterdetails.R
import com.starwarsmasterdetails.models.Film

class FilmsAdapter : PagingDataAdapter<Film, FilmsAdapter.FilmsViewHolder>(FilmsDiffCallback) {

    class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var filmName: TextView = itemView.findViewById(R.id.tvFilmItemName)

        fun bind(film: Film) {
            filmName.text = film.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val film: Film? = getItem(position)
        film?.let { holder.bind(film) }
    }

    object FilmsDiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }

}