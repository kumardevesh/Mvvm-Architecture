package com.practice.targetassignment.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.targetassignment.R
import com.practice.targetassignment.base.BaseActivity
import com.practice.targetassignment.model.ApiResponseState
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.util.ViewModelFactory
import com.practice.targetassignment.util.gone
import com.practice.targetassignment.util.show
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class RepoListActivity : BaseActivity(), RepoSelectedListener {


    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    private var viewModel: ListViewModel? = null
    private var adapter: RepoListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        observeViewModel()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
        adapter = RepoListAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        if (viewModel?.getRepos()?.value == null) {
            viewModel?.fetchRepos()
        }

        viewModel?.getRepos()?.observe(this, Observer { repos ->
            if (repos.isNullOrEmpty().not()) {
                shimmer_view_container.gone()
                tv_error.gone()
                recyclerView.show()
                adapter?.addData(repos)
            }
        })


        viewModel?.getLoadingState()?.observe(this, Observer { it ->
            when (it) {
                ApiResponseState.LOADING -> {
                    shimmer_view_container.show()
                    shimmer_view_container.startShimmerAnimation()
                    tv_error.gone()
                }
                ApiResponseState.COMPLETED -> {
                    shimmer_view_container.gone()
                    tv_error.gone()
                }
                ApiResponseState.FAILED -> {
                    shimmer_view_container.gone()
                    tv_error.show()

                }
                ApiResponseState.CACHED_RESULT -> {
                    shimmer_view_container.gone()
                    tv_error.gone()
                }
                else -> {
                }
            }
        })
    }

    override fun repoSelected(repo: Repo) {

    }
}
