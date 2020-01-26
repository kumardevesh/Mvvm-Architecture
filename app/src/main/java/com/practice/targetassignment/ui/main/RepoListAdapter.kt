package com.practice.targetassignment.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.practice.targetassignment.R
import com.practice.targetassignment.model.Repo

class RepoListAdapter(private val repoSelectedListener: RepoSelectedListener) :
    RecyclerView.Adapter<RepoItemViewHolder>() {

    private val repoList = ArrayList<Repo>()
    private var expandedPosition: Int = -1


    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_repo_list_item, parent, false)
        return RepoItemViewHolder(view, repoSelectedListener)
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bindTo(repoList.get(position))
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    fun addData(list: List<Repo>) {
        repoList.clear()
        repoList.addAll(list)
        notifyDataSetChanged()
    }

    fun updateExpandedPosition(position: Int) {
        if (expandedPosition == position) {
            expandedPosition = -1
        } else if (expandedPosition != -1) {
            repoList.get(expandedPosition).isExpandedState = false
            notifyItemChanged(expandedPosition)
            expandedPosition = position
        } else {
            expandedPosition = position
        }

    }
}
