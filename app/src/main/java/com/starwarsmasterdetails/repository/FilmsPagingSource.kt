package com.starwarsmasterdetails.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.starwarsmasterdetails.api.StarWarsAPI
import com.starwarsmasterdetails.models.Film
import com.starwarsmasterdetails.util.Constants.Companion.TAG
import java.io.IOException

class FilmsPagingSource(
    private val starWarsApi: StarWarsAPI,
    ) :
    PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = starWarsApi.getFilms(page = page)
            val film = ArrayList<Film>()
            if (response.isSuccessful) {
                response.body()?.let { film.addAll(it.results) }
            }
            LoadResult.Page(
                film,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (film.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            Log.e(TAG, "load: IOException - $exception")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "load: HttpException - $exception")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return null
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }

}
