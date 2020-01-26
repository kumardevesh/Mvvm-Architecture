package com.practice.targetassignment.repository

import com.practice.targetassignment.model.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService {

    @GET("repositories")
    fun getRepositories(
        @Query("language") language: String?,
        @Query("since") period: String?,
        @Query("spoken_language_code") spokenLanguage: String?
    ): Single<List<Repo>>
}