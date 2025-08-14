package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var recipesList: List<Recipe> = emptyList()

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
        arguments?.let { args ->
            categoryId = args.getInt(Constants.ARG_CATEGORY_ID)
            categoryName = args.getString(Constants.ARG_CATEGORY_NAME)
            categoryImageUrl = args.getString(Constants.ARG_CATEGORY_IMAGE_URL)
        }
        initRecycler()
        initHeader()
    }

    private fun initHeader() {
        val categoryName = arguments?.getString(Constants.ARG_CATEGORY_NAME) ?: "Категория"
        val categoryImageUrl = arguments?.getString(Constants.ARG_CATEGORY_IMAGE_URL) ?: ""

        binding.tvCategoryName.text = categoryName

        try {
            requireContext().assets.open(categoryImageUrl).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivCategoryImage.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            binding.ivCategoryImage.setImageResource(android.R.color.darker_gray)
        }
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val bundle = bundleOf(Constants.ARG_RECIPE_ID to recipeId)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun initRecycler() {
        recipesAdapter = RecipesListAdapter(recipesList) { recipeId ->
            openRecipeByRecipeId(recipeId)
        }
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = recipesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}