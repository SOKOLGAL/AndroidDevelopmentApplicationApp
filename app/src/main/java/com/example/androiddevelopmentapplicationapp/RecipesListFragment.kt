package com.example.androiddevelopmentapplicationapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.androidapplicationdevelopmentxml.R

class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { args ->
            categoryId = args.getInt(Constants.ARG_CATEGORY_ID)
            categoryName = args.getString(Constants.ARG_CATEGORY_NAME)
            categoryImageUrl = args.getString(Constants.ARG_CATEGORY_IMAGE_URL)
        }
        initRecycler()
    }

    private fun initRecycler() {
    }
}