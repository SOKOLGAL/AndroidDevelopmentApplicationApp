package com.example.androiddevelopmentapplicationapp.ui.recipes.favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentFavoritesBinding
import com.example.androiddevelopmentapplicationapp.Constants
import com.example.androiddevelopmentapplicationapp.model.Recipe
import com.example.androiddevelopmentapplicationapp.model.STUB
import com.example.androiddevelopmentapplicationapp.ui.RecipesListAdapter
import com.example.androiddevelopmentapplicationapp.ui.recipes.recipe.RecipeFragment

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("FragmentFavoritesBinding is null!")
    private lateinit var recipesListAdapter: RecipesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFavoritesTitle.text = getString(R.string.button_favorites)

        initRecycler()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initRecycler() {
        val favoriteRecipeIds = getFavorites()
        val favoriteRecipeIdsAsStrings = favoriteRecipeIds.map { it.toInt() }.toSet()
        val favoriteRecipes = STUB.getRecipesByIds(favoriteRecipeIdsAsStrings)

        recipesListAdapter = RecipesListAdapter(
            recipes = favoriteRecipes,
            onItemClick = { recipeId ->
                openRecipeByRecipeId(recipeId)
            }
        )

        binding.rvFavorites.apply {
            adapter = recipesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        updateFavoritesView(favoriteRecipes)
    }

    private fun updateFavoritesView(favoriteRecipes: List<Recipe>) {
        binding.ivFavoritesImage.setImageResource(R.drawable.bcg_favorites)
        binding.rvFavorites.isVisible = favoriteRecipes.isNotEmpty()
        binding.tvEmptyFavorites.isVisible = favoriteRecipes.isEmpty()
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = requireContext().getSharedPreferences(
            Constants.PREFS_FAVORITES,
            Context.MODE_PRIVATE
        )

        return HashSet(
            sharedPrefs?.getStringSet(Constants.KEY_FAVORITE_RECIPES, HashSet<String>())
                ?: mutableSetOf()
        )
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        try {
            val recipe = STUB.getRecipeById(recipeId)
            val bundle = Bundle().apply {
                putParcelable(Constants.ARG_RECIPE, recipe)
            }
            parentFragmentManager.commit {
                replace<RecipeFragment>(R.id.mainContainer, args = bundle)
                addToBackStack(null)
            }
        } catch (e: Exception) {
            Log.e("RecipesListFragment", "Error opening recipe", e)
            Toast.makeText(
                requireContext(),
                "Не удалось открыть рецепт",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}