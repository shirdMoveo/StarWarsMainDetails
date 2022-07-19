package com.starwarsmasterdetails.api

import com.starwarsmasterdetails.api.responses.FilmsResponse
import com.starwarsmasterdetails.api.responses.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsAPI {

    @GET("people")
    suspend fun getPeopleByName(
    @Query("search") name: String = "",
    @Query("page") page: Int
    ): Response<PeopleResponse>

    @GET("films")
    suspend fun getFilms( @Query("page") page: Int): Response<FilmsResponse>

}