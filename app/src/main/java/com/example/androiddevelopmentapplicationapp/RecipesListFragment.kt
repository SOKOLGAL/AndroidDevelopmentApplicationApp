package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentRecipesListBinding

class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("ActivityMainBinding is null!")

    private lateinit var recipesAdapter: RecipesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipes = STUB.burgerRecipes
        val adapter = RecipesListAdapter(recipes) { recipeId ->
            openRecipeByRecipeId(recipeId)
        }
        arguments?.let { args ->
            categoryId = args.getInt(Constants.ARG_CATEGORY_ID)
            categoryName = args.getString(Constants.ARG_CATEGORY_NAME)
            categoryImageUrl = args.getString(Constants.ARG_CATEGORY_IMAGE_URL).toString()
        }
        initRecycler()
        initHeader()

        binding.rvRecipes.adapter = adapter
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initHeader() {
        binding.tvCategoryName.text = categoryName

        try {
            requireContext().assets.open(categoryImageUrl.toString()).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivRecipeImage.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            binding.ivRecipeImage.setImageResource(android.R.color.darker_gray)
        }
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        try {
            val recipe = STUB.getRecipeById(recipeId)
            val bundle = Bundle().apply {
                putInt(Constants.ARG_RECIPE_ID, recipeId)
            }

            parentFragmentManager.commit {
                replace<RecipeFragment>(R.id.mainContainer, args = bundle)
                addToBackStack(null)
            }
        } catch (e: Exception) {
            Log.e("RecipesListFragment", "Error opening recipe", e)
            Toast.makeText(requireContext(), "Не удалось открыть рецепт", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecycler() {
        val recipes = STUB.getRecipesByCategoryId(categoryId)

        recipesAdapter = RecipesListAdapter(recipes) { recipeId ->
            openRecipeByRecipeId(recipeId)
        }

        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = recipesAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun openRecipeDetails(recipe: Recipe) {
        val bundle = bundleOf(
            Constants.ARG_RECIPE to recipe
        )
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }
}