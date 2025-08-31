package com.example.androiddevelopmentapplicationapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentFavoritesBinding
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddevelopmentapplicationapp.Constants.PREFS_FAVORITES

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
            onItemClick = { recipe ->
                openRecipeByRecipeId(recipe.id)
            }
        )

        binding.rvFavorites.apply {
            adapter = recipesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        updateFavoritesView(favoriteRecipes)
    }

    private fun updateFavoritesView(favoriteRecipes: List<Recipe>) {
        binding.ivFavoritesImage.setImageResource(
            if (favoriteRecipes.isEmpty())
                R.drawable.bcg_favorites
            else
                R.drawable.bcg_recipes_list
        )

        binding.rvFavorites.isVisible = favoriteRecipes.isNotEmpty()
        binding.tvEmptyFavorites.isVisible = favoriteRecipes.isEmpty()
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = requireContext().getSharedPreferences(
            PREFS_FAVORITES,
            Context.MODE_PRIVATE
        )

        return HashSet(
            sharedPrefs?.getStringSet(Constants.KEY_FAVORITE_RECIPES, HashSet<String>())
                ?: mutableSetOf()
        )
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipeFragment = RecipeFragment().apply {
            arguments = Bundle().apply {
                putInt(Constants.ARG_RECIPE_ID, recipeId)
            }
        }

        parentFragmentManager.commit {
            replace(R.id.mainContainer, RecipeFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.ARG_RECIPE_ID, recipeId)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}