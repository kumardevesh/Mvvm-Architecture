package com.practice.targetassignment.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.util.getDescription
import com.practice.targetassignment.util.gone
import com.practice.targetassignment.util.loadCircularImage
import com.practice.targetassignment.util.show
import kotlinx.android.synthetic.main.view_repo_list_item.view.*

class RepoItemViewHolder(
    itemView: View,
    private val repoSelectedListener: RepoSelectedListener
) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(repo: Repo) {
        itemView.setOnClickListener {
            repo.isExpandedState = repo.isExpandedState.not()
            setExpandedState(repo.isExpandedState)
            repoSelectedListener.repoSelected(adapterPosition)
        }
        itemView.tv_author_name.text = repo.author
        itemView.tv_repo_name.text = repo.name
        itemView.iv_avatar.loadCircularImage(repo.avatar)
        itemView.tv_repo_description.text = repo.getDescription()
        if (repo.language.isNullOrEmpty().not()) {
            itemView.language_container.show()
            itemView.tv_language.text = repo.language
        } else {
            itemView.language_container.gone()
        }
        if (repo.stars > 0) {
            itemView.tv_stars.show()
            itemView.tv_stars.text = repo.stars.toString()
        } else {
            itemView.tv_stars.gone()
        }

        if (repo.forks > 0) {
            itemView.tv_forks.show()
            itemView.tv_forks.text = repo.forks.toString()
        } else {
            itemView.tv_forks.gone()
        }

        setExpandedState(repo.isExpandedState)
    }

    fun setExpandedState(expandedStateEnabled: Boolean) {
        if (expandedStateEnabled) {
            itemView.detail_container.show()
        } else {
            itemView.detail_container.gone()
        }
    }
}