package com.starwarsmasterdetails.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starwarsmasterdetails.R
import com.starwarsmasterdetails.models.People

class PeopleAdapter(
    private var onItemClickListener: ((People) -> Unit)
): PagingDataAdapter<People, PeopleAdapter.PeopleViewHolder>(PeopleDiffCallback) {


    class PeopleViewHolder(itemView: View, val onClick: (People) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private var peopleName: TextView = itemView.findViewById(R.id.tvPeopleNameTextView)
        private var currentPeople: People? = null

        init {
            itemView.setOnClickListener {
                currentPeople?.let {
                    onClick(it)
                }
            }
        }

        fun bind(people: People) {
            currentPeople = people
            peopleName.text = people.name
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_people, parent, false)
        return PeopleViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val people: People? = getItem(position)
        people?.let { holder.bind(people) }
    }

    object PeopleDiffCallback : DiffUtil.ItemCallback<People>() {
        override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem == newItem
        }
    }

}