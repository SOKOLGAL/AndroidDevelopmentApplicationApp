package com.example.androiddevelopmentapplicationapp

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
    val description: String
)