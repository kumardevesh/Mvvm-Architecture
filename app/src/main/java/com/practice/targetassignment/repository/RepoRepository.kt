package com.practice.targetassignment.repository

import android.content.Context
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.util.SharedPrefUtil
import io.reactivex.Single
import javax.inject.Inject

class RepoRepository @Inject
constructor(
    private val repoService: RepoService,
    private val appContext: Context
) {

    companion object {
        const val JAVA = "java"
        const val WEEK = "weekly"
    }

    fun getRepositories(language: String = JAVA, period: String = WEEK): Single<List<Repo>> {
        return repoService.getRepositories(language, period).doOnSuccess {
            if (it.isNullOrEmpty().not()) {
                updateCachedRepos(it)
            }
        }
    }

    fun getCachedRepos(): List<Repo>? {
        return SharedPrefUtil.getRepoList(appContext)
    }

    fun updateCachedRepos(list: List<Repo>) {
        SharedPrefUtil.putRepoList(appContext, list)
    }
}