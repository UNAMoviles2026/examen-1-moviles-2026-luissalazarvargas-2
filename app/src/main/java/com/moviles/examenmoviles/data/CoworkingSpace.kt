package com.moviles.examenmoviles.data

data class CoworkingSpace (
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val capacity: Int,
    val pricePerHour: Double,
    val isAvailable: Boolean
)