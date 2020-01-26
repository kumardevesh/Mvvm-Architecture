package com.practice.targetassignment.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.practice.targetassignment.R
import com.practice.targetassignment.model.Repo

class RepoListAdapter(private val repoSelectedListener: RepoSelectedListener) :
    RecyclerView.Adapter<RepoItemViewHolder>() {

    private val data = ArrayList<Repo>()


    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_repo_list_item, parent, false)
        return RepoItemViewHolder(view, repoSelectedListener)
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bindTo(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(list: List<Repo>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}
