package com.example.androiddevelopmentapplicationapp

@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String
)

annotation class Parcelize
