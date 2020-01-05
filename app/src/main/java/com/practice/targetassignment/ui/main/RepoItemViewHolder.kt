package com.practice.targetassignment.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.targetassignment.model.Repo
import kotlinx.android.synthetic.main.view_repo_list_item.view.*

class RepoItemViewHolder(
    itemView: View,
    private val repoSelectedListener: RepoSelectedListener
) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(repo: Repo) {
        itemView.setOnClickListener {
            repoSelectedListener.repoSelected(repo)
        }
        itemView.tv_description.text = repo.repoDescription?.description
        itemView.tv_name.text = repo.repoDescription?.name
        itemView.tv_user_name.text = repo.name
        Glide.with(itemView.context).load(repo.avatar).into(itemView.iv_avatar)

    }
}