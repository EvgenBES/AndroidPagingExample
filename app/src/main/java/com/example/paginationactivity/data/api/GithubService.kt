package com.example.paginationactivity.data.api

import com.example.paginationactivity.model.Repo
import kotlinx.coroutines.delay
import kotlin.random.Random

class GithubService {

    suspend fun searchRepos(apiQuery: String, position: Int, loadSize: Int): RepoSearchResponse {
        delay(500)
        return RepoSearchResponse(items = getFakeData(position, loadSize))
    }

    private fun getFakeData(position: Int, loadSize: Int): List<Repo> {
        val mutableList: MutableList<Repo> = mutableListOf()

        val error = Random.nextInt(0, 4)

        if (error >= 3) {
            throw IllegalAccessError("message")
        }

        for (i in 0..loadSize) {
            mutableList.add(
                Repo(
                    id = i,
                    name = "Page$position"
                )
            )
        }

        return mutableList
    }

}