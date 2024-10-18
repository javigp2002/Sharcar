package com.sharcar.entities

data class Enterprise(
    val id: Int,
    val name: String,
    val locations: List<Locations>,
)
