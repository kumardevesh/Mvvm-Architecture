package com.practice.targetassignment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.targetassignment.di.util.RxSingleSchedulers
import com.practice.targetassignment.model.ApiResponseState
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.repository.RepoRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ListViewModel @Inject
constructor(
    private val repoRepository: RepoRepository,
    private val schedulerProvider: RxSingleSchedulers
) : ViewModel() {
    private var disposable: CompositeDisposable? = null

    private val repos = MutableLiveData<List<Repo>>()
    private val loadState = MutableLiveData<ApiResponseState>()

    init {
        disposable = CompositeDisposable()
    }

    fun getLoadingState(): LiveData<ApiResponseState> {
        return loadState
    }

    fun getRepos(): MutableLiveData<List<Repo>> {
        return repos
    }

    fun fetchRepos() {
        fun fetchCachedResult() {
            val cachedResult = repoRepository.getCachedRepos()
            repos.value = cachedResult
            loadState.value = if (cachedResult.isNullOrEmpty()) ApiResponseState.FAILED else
                ApiResponseState.CACHED_RESULT


        }
        loadState.value = ApiResponseState.LOADING
        disposable?.add(
            repoRepository.getRepositories().compose(schedulerProvider.applySchedulers()).subscribeWith(object :
                    DisposableSingleObserver<List<Repo>>() {
                    override fun onSuccess(value: List<Repo>) {
                        if (value.isNullOrEmpty().not()) {
                            repos.value = value
                            loadState.value = ApiResponseState.COMPLETED
                        } else {
                            fetchCachedResult()
                        }
                    }

                    override fun onError(e: Throwable) {
                        fetchCachedResult()
                    }
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }
}