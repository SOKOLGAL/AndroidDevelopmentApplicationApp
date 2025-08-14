package com.example.androiddevelopmentapplicationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment() {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("CategoriesListFragment is null!")

    private lateinit var adapter: CategoriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val categories = STUB.getCategories()

        adapter = CategoriesListAdapter(categories)

        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCategories.adapter = adapter

        adapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category = STUB.getCategories().find { it.id == categoryId }
        val categoryName = category?.title ?: "Unknown category"
        val categoryImageUrl = category?.imageUrl ?: ""

        val bundle = bundleOf(
            Constants.ARG_CATEGORY_ID to categoryId,
            Constants.ARG_CATEGORY_NAME to categoryName,
            Constants.ARG_CATEGORY_IMAGE_URL to categoryImageUrl
        )

        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun openCategoryRecipes(categoryId: Int) {
        val bundle = bundleOf(
            Constants.ARG_CATEGORY_ID to categoryId,
            Constants.ARG_CATEGORY_NAME to "Бургеры",
            Constants.ARG_CATEGORY_IMAGE_URL to "bcg_recipes_list.png"  // Файл в папке assets
        )

        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}