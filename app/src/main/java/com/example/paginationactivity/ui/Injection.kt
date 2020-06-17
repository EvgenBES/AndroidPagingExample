package com.example.paginationactivity.ui

import androidx.lifecycle.ViewModelProvider
import com.example.paginationactivity.data.api.GithubService
import com.example.paginationactivity.data.source.GithubRepository

object Injection {

    private fun provideGithubRepository(): GithubRepository {
        return GithubRepository(GithubService())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}
