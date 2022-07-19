package com.starwarsmasterdetails.models

data class Vehicle(
    val name: String,
    val model: String,
    val vehicle_class: String,
    val manufacturer: String,
    val length: String,
    val cost_in_credits: String,
    val crew: String,
    val passengers: String,
    val max_atmosphering_speed: String,
    val cargo_capacity: String,
    val consumables: String,
    val films: ArrayList<String>,
    val pilots: ArrayList<String>,
    val url: String,
    val created: String,
    val edited: String,
    )
