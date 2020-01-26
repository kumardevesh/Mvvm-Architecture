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

    companion object {
        private val REPO_EXPIRY_THRESHOLD = 2 * 60 * 60 * 1000 // 2 hrs
    }
    private val prefName = "appSharedPref"
    private val KEY_REPO_LIST = "repoList"
    private val KEY_REPO_TSTAMP = "lastRepoUpdateTstamp"


    fun getSharedpreferences() = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun getRepoList(): List<Repo>? {
        val str = getSharedpreferences().getString(KEY_REPO_LIST, "")
        val lastRepoFetchTstamp = getSharedpreferences().getLong(KEY_REPO_TSTAMP, 0L)
        if (str == null || System.currentTimeMillis() - lastRepoFetchTstamp > REPO_EXPIRY_THRESHOLD) {
            return null
        } else {
            val type = object : TypeToken<List<Repo>>() {}.type
            return Gson().fromJson<List<Repo>>(str, type)
        }

    }

    fun putRepoList(repoList: List<Repo>) {
        getSharedpreferences().edit().putString(KEY_REPO_LIST, Gson().toJson(repoList)).apply()
        getSharedpreferences().edit().putLong(KEY_REPO_TSTAMP, System.currentTimeMillis()).apply()
    }
}


