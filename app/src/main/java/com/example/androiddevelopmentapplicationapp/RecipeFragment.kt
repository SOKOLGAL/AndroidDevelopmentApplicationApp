package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("FragmentRecipeBinding is null!")

    private var recipeId: Int = 0
    private lateinit var recipe: Recipe

    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            recipeId = arguments?.getInt(Constants.ARG_RECIPE_ID) ?: 0
            Log.e("RecipeFragment", "RecipeId: $recipeId")

            recipe = try {
                STUB.getRecipeById(recipeId)
            } catch (e: Exception) {
                Log.e("RecipeFragment", "Error getting recipe", e)
                null
            } ?: throw IllegalArgumentException("Recipe not found")

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                    arguments?.getParcelable(Constants.ARG_RECIPE, Recipe::class.java)
                }

                else -> {
                    @Suppress("DEPRECATION")
                    arguments?.getParcelable(Constants.ARG_RECIPE)
                }
            }
        } catch (e: Exception) {
            Log.e("RecipeFragment", "Error processing recipe", e)
            Toast.makeText(
                requireContext(),
                "Не удалось загрузить рецепт: ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun createMaterialDivider(): MaterialDividerItemDecoration {
        return MaterialDividerItemDecoration(
            requireContext(),
            MaterialDividerItemDecoration.HORIZONTAL
        ).apply {
            isLastItemDecorated = false
        }
    }

    private fun initRecyclers() {
        ingredientsAdapter = IngredientsAdapter(recipe.ingredients)
        val ingredientsDivider = MaterialDividerItemDecoration(
            requireContext(),
            MaterialDividerItemDecoration.VERTICAL
        )
        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ingredientsDivider)
        }
    }

    private fun initUI() {
        binding.tvRecipeTitle.text = recipe.title
        try {
            requireContext().assets.open(recipe.imageUrl).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivRecipeImage.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            Log.e("RecipeFragment", "Error loading image", e)
            binding.ivRecipeImage.setImageResource(android.R.color.darker_gray)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}