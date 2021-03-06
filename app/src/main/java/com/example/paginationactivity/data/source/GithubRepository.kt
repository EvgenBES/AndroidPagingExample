package com.example.paginationactivity.data.source

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paginationactivity.data.api.GithubService
import com.example.paginationactivity.model.Repo
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val service: GithubService) {

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        Log.d("GithubRepository", "New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
