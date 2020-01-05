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
    private val sortedData = ArrayList<Repo>()
    private var sortingEnabled: Boolean = false
    private var searchData: ArrayList<Repo>? = null


    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_repo_list_item, parent, false)
        return RepoItemViewHolder(view, repoSelectedListener)
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        val data = searchData?.get(position) ?: if (sortingEnabled) sortedData.get(position) else data.get(position)
        holder.bindTo(data)

    }

    override fun getItemCount(): Int {
        return if (searchData == null) data.size else searchData?.size ?: 0
    }

    fun addData(repoList: List<Repo>) {
        data.clear()
        data.addAll(repoList)
        sortedData.clear()
        sortedData.addAll(repoList)
        sortedData.sortBy { it.name }
        notifyDataSetChanged()
    }

    fun toggleSorting() {
        sortingEnabled = !sortingEnabled
        notifyDataSetChanged()
    }

    fun showSearchResult(query: String?) {
        if (query.isNullOrBlank()) {
            searchData = null
        } else {
            searchData = ArrayList(data.filter {
                (it.userName?.contains(query, true) == true || it.name?.contains(query, true) == true)
            })
        }
        notifyDataSetChanged()
    }

}
