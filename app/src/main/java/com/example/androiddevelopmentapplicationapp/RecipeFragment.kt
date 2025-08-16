package com.example.androiddevelopmentapplicationapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentRecipesListBinding

class RecipeFragment : Fragment(R.layout.fragment_recipes_list) {
    private lateinit var binding: FragmentRecipesListBinding
    private var recipeId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            binding = FragmentRecipesListBinding.bind(view)

            recipeId = arguments?.getInt(Constants.ARG_RECIPE_ID) ?: 0
            Log.e("RecipeFragment", "RecipeId: $recipeId")

            val recipe = try {
                STUB.getRecipeById(recipeId)
            } catch (e: Exception) {
                Log.e("RecipeFragment", "Error getting recipe", e)
                null
            }

            recipe?.let {
                binding.tvCategoryName.text = it.title
            } ?: run {
                binding.tvCategoryName.text = "Рецепт не найден"
                Log.e("RecipeFragment", "Recipe is null")
            }
        } catch (e: Exception) {
            Log.e("RecipeFragment", "Critical error in onViewCreated", e)

            Toast.makeText(requireContext(), "Ошибка загрузки рецепта", Toast.LENGTH_SHORT).show()
        }
    }
}