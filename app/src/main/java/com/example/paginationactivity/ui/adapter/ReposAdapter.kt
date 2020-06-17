package com.example.paginationactivity.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.paginationactivity.R
import com.example.paginationactivity.model.Repo

class ReposAdapter(
    private val clickListener: (item: Repo) -> Unit
) : PagingDataAdapter<Repo, RepoViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }

}