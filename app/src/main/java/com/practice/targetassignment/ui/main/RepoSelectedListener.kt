package com.practice.targetassignment.ui.main

import com.practice.targetassignment.model.Repo

interface RepoSelectedListener {
    fun repoSelected(repo: Repo)
}