package com.starwarsmasterdetails.models

data class Planet(
    val name: String,
    val diameter: String,
    val rotation_period: String,
    val orbital_period: String,
    val gravity: String,
    val population: String,
    val climate: String,
    val terrain: String,
    val residents: ArrayList<String>,
    val films: ArrayList<String>,
    val url: String,
    val created: String,
    val edited: String,
)
