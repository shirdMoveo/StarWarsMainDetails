package com.starwarsmasterdetails.api.responses

import com.starwarsmasterdetails.models.People

data class PeopleResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<People>
)
