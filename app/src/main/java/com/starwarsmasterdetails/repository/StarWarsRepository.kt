package com.starwarsmasterdetails.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.starwarsmasterdetails.api.StarWarsAPI
import com.starwarsmasterdetails.models.Film
import com.starwarsmasterdetails.models.People
import com.starwarsmasterdetails.network.NetworkManager
import kotlinx.coroutines.flow.Flow

class StarWarsRepository {


    private val starWarsApi = NetworkManager.getServiceApi(StarWarsAPI::class.java)

    fun getAllPeople(): Flow<PagingData<People>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { PeopleListPagingSource(starWarsApi) }
        ).flow
    }

    fun getPeopleByName(name: String): Flow<PagingData<People>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { PeopleListPagingSource(starWarsApi, name) }
        ).flow
    }

    fun getAllFilms(): Flow<PagingData<Film>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { FilmsPagingSource(starWarsApi) }
        ).flow
    }


    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }

}