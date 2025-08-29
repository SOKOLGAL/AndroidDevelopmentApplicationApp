package com.example.androiddevelopmentapplicationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentFavoritesBinding
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("FragmentFavoritesBinding is null!")
    private lateinit var recipesListAdapter: RecipesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFavoritesTitle.text = getString(R.string.button_favorites)

        initRecycler()
        loadFavoriteRecipes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    private fun initRecycler() {
        recipesListAdapter = RecipesListAdapter() { recipe ->

            openRecipeByRecipeId(recipe.id)
        }

        binding.rvFavorites.apply {
            adapter = recipesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadFavoriteRecipes() {
        val favoriteRecipeIds = getFavorites()
        val favoriteRecipeIdsAsStrings = favoriteRecipeIds.map { it.toString() }.toSet()
        val favoriteRecipes = STUB.getRecipesByIds(favoriteRecipeIdsAsStrings)

        recipesListAdapter.submitList(favoriteRecipes)
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
        binding.rvFavorites.isVisible = favoriteRecipes.isEmpty()
    }

    private fun getFavorites(): Set<Int> {
        return RecipeFragment.getFavorites(requireContext())
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesToRecipeDetails(recipeId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}