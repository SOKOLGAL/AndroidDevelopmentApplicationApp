package com.example.androiddevelopmentapplicationapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.androiddevelopmentapplicationapp.model.Category
import com.example.androiddevelopmentapplicationapp.model.Ingredient
import com.example.androiddevelopmentapplicationapp.model.Recipe

class RecipeViewModel() : ViewModel() {

    data class RecipeDetailsState(
        val category: Category? = null,
        val ingredient: Ingredient? = null,
        val recipe: Recipe? = null,
        val recipes: List<Recipe> = listOf(),
        val ingredients: List<Ingredient> = listOf(),
        val dataset: List<Category> = listOf(),
    )
}