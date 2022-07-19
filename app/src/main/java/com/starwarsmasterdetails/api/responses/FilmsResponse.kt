package com.starwarsmasterdetails.api.responses

import com.starwarsmasterdetails.models.Film
import com.starwarsmasterdetails.models.People

data class FilmsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Film>
)
