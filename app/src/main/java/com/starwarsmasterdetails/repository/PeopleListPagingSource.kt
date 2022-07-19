package com.starwarsmasterdetails.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.starwarsmasterdetails.api.StarWarsAPI
import com.starwarsmasterdetails.models.People
import com.starwarsmasterdetails.util.Constants.Companion.TAG
import java.io.IOException

class PeopleListPagingSource(
    private val starWarsApi: StarWarsAPI,
    private val searchName: String = ""
    ) :
    PagingSource<Int, People>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, People> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = starWarsApi.getPeopleByName(
                name = searchName,
                page = page,
            )
            val people = ArrayList<People>()
            if (response.isSuccessful) {
                response.body()?.let { people.addAll(it.results) }
            }
            LoadResult.Page(
                people,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (people.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            Log.e(TAG, "load: IOException - $exception")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "load: HttpException - $exception")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, People>): Int? {
        return null
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }

}
