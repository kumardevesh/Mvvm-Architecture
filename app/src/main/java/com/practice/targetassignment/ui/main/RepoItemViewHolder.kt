package com.practice.targetassignment.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.util.loadCircularImage
import kotlinx.android.synthetic.main.view_repo_list_item.view.*

class RepoItemViewHolder(
    itemView: View,
    private val repoSelectedListener: RepoSelectedListener
) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(repo: Repo) {
        itemView.setOnClickListener {
            repoSelectedListener.repoSelected(repo)
        }
        itemView.tv_author_name.text = repo.author
        itemView.tv_repo_name.text = repo.name
        itemView.iv_avatar.loadCircularImage(repo.avatar)
    }
}