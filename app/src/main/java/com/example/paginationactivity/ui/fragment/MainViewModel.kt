package com.example.paginationactivity.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paginationactivity.data.source.GithubRepository
import com.example.paginationactivity.model.Repo
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: GithubRepository): ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<Repo>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Repo>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}