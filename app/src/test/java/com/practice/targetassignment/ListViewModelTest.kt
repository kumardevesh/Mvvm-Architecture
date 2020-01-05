package com.practice.targetassignment

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.practice.targetassignment.model.ApiResponseState
import com.practice.targetassignment.model.Repo
import com.practice.targetassignment.repository.RepoRepository
import com.practice.targetassignment.repository.RepoService
import com.practice.targetassignment.ui.main.ListViewModel
import com.practice.targetassignment.util.SchedulerProvider
import com.practice.targetassignment.util.SharedPrefUtil
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException


@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repoService: RepoService


    @Mock
    lateinit var appContext: Context

    @Mock
    lateinit var repoRepository: RepoRepository

    @Mock
    lateinit var schedularProvider: SchedulerProvider

    @Mock
    lateinit var apiStateObserver: Observer<ApiResponseState>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    lateinit var listViewModel: ListViewModel

    lateinit var lifecycle: Lifecycle

    @Before

    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        listViewModel = ListViewModel(repoRepository, schedularProvider)
        listViewModel.getLoadingState().observeForever(apiStateObserver)
    }


    @Test
    fun testApiSuccess() {
        Mockito.`when`(this.repoRepository.getRepositories()).thenAnswer {
            return@thenAnswer Single.just(listOf(Repo()))
        }

        listViewModel.fetchRepos()
        Mockito.verify(apiStateObserver.onChanged(ApiResponseState.LOADING))
        Mockito.verify(apiStateObserver.onChanged(ApiResponseState.COMPLETED))
    }

    @Test
    fun testCachedResult() {
        Mockito.`when`(SharedPrefUtil.getRepoList(appContext)).thenReturn(listOf(Repo()))
        Mockito.`when`(this.repoRepository.getRepositories()).thenAnswer {
            return@thenAnswer Single.error<Throwable>(UnknownHostException())
        }

        listViewModel.fetchRepos()
        Mockito.verify(apiStateObserver.onChanged(ApiResponseState.LOADING))
        Mockito.verify(apiStateObserver.onChanged(ApiResponseState.CACHED_RESULT))
    }

    @Test
    fun testApiFailure() {
        Mockito.`when`(SharedPrefUtil.getRepoList(appContext)).thenReturn(null)
        Mockito.`when`(this.repoRepository.getRepositories()).thenAnswer {
            return@thenAnswer Single.error<Throwable>(UnknownHostException())
        }

        listViewModel.fetchRepos()
        Mockito.verify(apiStateObserver.onChanged(ApiResponseState.LOADING))
        Mockito.verify(apiStateObserver.onChanged(ApiResponseState.FAILED))
    }



}
