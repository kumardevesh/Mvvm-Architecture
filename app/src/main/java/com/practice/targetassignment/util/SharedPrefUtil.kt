package com.practice.targetassignment.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.targetassignment.model.Repo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefUtil
@Inject
constructor(private val context: Context) {

    private val prefName = "appSharedPref"
    private val KEY_REPO_LIST = "repoList"

    fun getSharedpreferences() = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun getRepoList(): List<Repo>? {
        val str = getSharedpreferences().getString(KEY_REPO_LIST, "")
        if (str == null) {
            return null
        } else {
            val type = object : TypeToken<List<Repo>>() {}.type
            return Gson().fromJson<List<Repo>>(str, type)
        }

    }

    fun putRepoList(repoList: List<Repo>) {
        getSharedpreferences().edit().putString(KEY_REPO_LIST, Gson().toJson(repoList)).apply()
    }
}


