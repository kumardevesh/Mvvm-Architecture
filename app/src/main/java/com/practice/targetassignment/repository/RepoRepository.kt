package com.practice.targetassignment.repository

import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.util.SharedPrefUtil
import io.reactivex.Single
import javax.inject.Inject

class RepoRepository @Inject
constructor(
    private val repoService: RepoService,
    private val sharedPrefUtil: SharedPrefUtil
) {

    fun getRepositories(
        language: String? = null,
        period: String? = null,
        spokenLanguage: String? = null
    ): Single<List<Repo>> {

        return repoService.getRepositories(language, period, spokenLanguage).doOnSuccess {
            if (it.isNullOrEmpty().not()) {
                updateCachedRepos(it)
            }
        }

    }

    fun getCachedRepos(): List<Repo>? {
        return sharedPrefUtil.getRepoList()
    }

    fun updateCachedRepos(list: List<Repo>) {
        sharedPrefUtil.putRepoList(list)
    }
}