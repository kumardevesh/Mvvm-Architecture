package com.practice.targetassignment.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.targetassignment.model.Repo


object SharedPrefUtil {

    private const val prefName = "appSharedPref"
    private const val KEY_REPO_LIST = "repoList"

    fun getSharedpreferences(context: Context) = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun getRepoList(context: Context): List<Repo>? {
        val str = getSharedpreferences(context).getString(KEY_REPO_LIST, "")
        if (str == null) {
            return null
        } else {
            val type = object : TypeToken<List<Repo>>() {}.type
            return Gson().fromJson<List<Repo>>(str, type)
        }

    }

    fun putRepoList(context: Context, repoList: List<Repo>) {
        getSharedpreferences(context).edit().putString(KEY_REPO_LIST, Gson().toJson(repoList)).apply()
    }
}


