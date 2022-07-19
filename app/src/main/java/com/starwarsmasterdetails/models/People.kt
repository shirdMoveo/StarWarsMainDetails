package com.starwarsmasterdetails.models

data class People (
    val name: String,
    val birth_year: String,
    val eye_color: String,
    val gender: String,
    val hair_color: String,
    val height: String,
    val mass: String,
    val skin_color: String,
    val homeworld: String,
    val films: ArrayList<String>,
    val species: ArrayList<String>,
    val starships: ArrayList<String>,
    val vehicles: ArrayList<String>,
    val url: String,
    val created: String,
    val edited: String,
)