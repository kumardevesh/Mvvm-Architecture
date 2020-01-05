package com.practice.targetassignment.repository

import com.practice.targetassignment.model.Repo
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService {

    @GET("developers")
    fun getRepositories(
        @Query("language") language: String,
        @Query("since") period: String
    ): Single<List<Repo>>
}