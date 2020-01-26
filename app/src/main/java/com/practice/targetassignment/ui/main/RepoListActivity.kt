package com.practice.targetassignment.ui.main

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.practice.targetassignment.R
import com.practice.targetassignment.base.BaseActivity
import com.practice.targetassignment.model.ApiResponseState
import com.practice.targetassignment.util.ViewModelFactory
import com.practice.targetassignment.util.gone
import com.practice.targetassignment.util.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import javax.inject.Inject

class RepoListActivity : BaseActivity(), RepoSelectedListener, SwipeRefreshLayout.OnRefreshListener {



    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    private var viewModel: RepoListViewModel? = null
    private var adapter: RepoListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        observeViewModel()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoListViewModel::class.java)
        adapter = RepoListAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        tv_retry.setOnClickListener {
            viewModel?.fetchRepos()
        }
        swipe_refresh_container.setOnRefreshListener(this)
    }


    private fun observeViewModel() {
        if (viewModel?.getRepos()?.value == null) {
            viewModel?.fetchRepos()
        }

        viewModel?.getRepos()?.observe(this, Observer { repos ->
            if (repos.isNullOrEmpty().not()) {
                shimmer_view_container.gone()
                error_layout.gone()
                recyclerView.show()
                adapter?.addData(repos)
            }
        })


        viewModel?.getLoadingState()?.observe(this, Observer { it ->
            when (it) {
                ApiResponseState.LOADING -> {
                    if (swipe_refresh_container.isRefreshing.not()) {
                        shimmer_view_container.show()
                        shimmer_view_container.startShimmerAnimation()
                    }
                    error_layout.gone()
                }
                ApiResponseState.COMPLETED -> {
                    swipe_refresh_container.isRefreshing = false
                    shimmer_view_container.gone()
                    error_layout.gone()
                }
                ApiResponseState.FAILED -> {
                    swipe_refresh_container.isRefreshing = false
                    shimmer_view_container.gone()
                    error_layout.show()

                }
                ApiResponseState.CACHED_RESULT -> {
                    swipe_refresh_container.isRefreshing = false
                    shimmer_view_container.gone()
                    error_layout.gone()
                }
                else -> {
                }
            }
        })
    }

    override fun repoSelected(position: Int) {
        adapter?.updateExpandedPosition(position)

        if (position + 1 == adapter?.itemCount) {
            Handler().postDelayed({
                recyclerView.scrollBy(0,300)
            },16)

        }
    }

    override fun onRefresh() {
        viewModel?.fetchRepos()
    }
}
