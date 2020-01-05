package com.practice.targetassignment.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.targetassignment.R
import com.practice.targetassignment.base.BaseActivity
import com.practice.targetassignment.model.ApiResponseState
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.ui.repoDetail.RepoDetailActivity
import com.practice.targetassignment.util.ViewModelFactory
import com.practice.targetassignment.util.gone
import com.practice.targetassignment.util.show
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity(), RepoSelectedListener {


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
        iv_filter.setOnClickListener {
            adapter?.toggleSorting()
        }

        val querySubject: PublishSubject<String?> = PublishSubject.create()

        val queryListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                querySubject.onNext(query)
                return true
            }
        }
        searchView.setOnQueryTextListener(queryListener)

        querySubject.debounce(300, TimeUnit.MILLISECONDS)
            .map { it?.trim() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNullOrEmpty()) {
                    iv_filter.show()
                } else {
                    iv_filter.gone()
                }
                adapter?.showSearchResult(it)
            }


    }

    private fun observeViewModel() {
        viewModel?.getRepos()?.observe(this, Observer { repos ->
            if (repos.isNullOrEmpty().not()) {
                progressBar.gone()
                tv_error.gone()
                recyclerView.show()
                iv_filter.show()
                searchView.show()

                adapter?.addData(repos)
            }
        })

        viewModel?.getLoadingState()?.observe(this, Observer { it ->
            when (it) {
                ApiResponseState.LOADING -> {
                    progressBar.show()
                    tv_error.gone()
                }
                ApiResponseState.COMPLETED -> {
                    progressBar.gone()
                    tv_error.gone()
                }
                ApiResponseState.FAILED -> {
                    progressBar.gone()
                    tv_error.show()
                    tv_error.text = getString(R.string.oops_message)

                }
                ApiResponseState.CACHED_RESULT -> {
                    progressBar.gone()
                    tv_error.gone()
                    Toast.makeText(this, R.string.cache_message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })
    }

    override fun repoSelected(repo: Repo) {
        val intent = Intent(this, RepoDetailActivity::class.java)
        intent.putExtra(RepoDetailActivity.KEY_REPO, repo)
        startActivity(intent)
    }
}
