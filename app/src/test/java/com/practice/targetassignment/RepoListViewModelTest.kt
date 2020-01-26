package com.practice.targetassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.practice.targetassignment.di.util.RxSingleSchedulers
import com.practice.targetassignment.model.ApiResponseState
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.repository.RepoRepository
import com.practice.targetassignment.ui.main.RepoListViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException


@RunWith(MockitoJUnitRunner::class)
class RepoListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repoRepository: RepoRepository

    @Mock
    lateinit var apiStateObserver: Observer<ApiResponseState>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    lateinit var repoListViewModel: RepoListViewModel

    lateinit var lifecycle: Lifecycle

    @Before

    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        repoListViewModel = RepoListViewModel(repoRepository, RxSingleSchedulers.TEST_SCHEDULER)
        repoListViewModel.getLoadingState().observeForever(apiStateObserver)
    }


    @Test
    fun testApiSuccess() {
        Mockito.`when`(this.repoRepository.getRepositories()).thenAnswer {
            return@thenAnswer Single.just(listOf(getDummyRepo()))
        }
        repoListViewModel.fetchRepos()
        verify(apiStateObserver).onChanged(ApiResponseState.LOADING)
        verify(apiStateObserver).onChanged(ApiResponseState.COMPLETED)
    }

    @Test
    fun testCachedResult() {
        Mockito.`when`(this.repoRepository.getRepositories()).thenAnswer {
            return@thenAnswer Single.error<Throwable>(UnknownHostException())
        }
        Mockito.`when`(this.repoRepository.getCachedRepos()).thenAnswer {
            return@thenAnswer listOf(getDummyRepo())
        }
        repoListViewModel.fetchRepos()
        verify(apiStateObserver).onChanged(ApiResponseState.LOADING)
        verify(apiStateObserver).onChanged(ApiResponseState.CACHED_RESULT)
    }

    @Test
    fun testApiFailure() {

        Mockito.`when`(this.repoRepository.getCachedRepos()).thenAnswer {
            return@thenAnswer listOf<Repo>()
        }

        Mockito.`when`(this.repoRepository.getRepositories()).thenAnswer {
            return@thenAnswer Single.error<Throwable>(UnknownHostException())
        }

        repoListViewModel.fetchRepos()
        verify(apiStateObserver).onChanged(ApiResponseState.LOADING)
        verify(apiStateObserver).onChanged(ApiResponseState.FAILED)
    }

    fun getDummyRepo(): Repo {
        return Repo(name = "abc", author = "abc", url = "http://abc.com")
    }



}
