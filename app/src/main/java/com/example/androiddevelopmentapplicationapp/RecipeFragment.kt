package com.example.androiddevelopmentapplicationapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.view.View
import androidx.fragment.app.Fragment
import com.example.androidapplicationdevelopmentxml.R

class RecipeFragment : Fragment(R.layout.fragment_recipes_list) {

    private var recipeId: Int? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeId = arguments?.getInt("recipe_id")

        val textView = view.findViewById<TextView>(R.id.tvCategoryName)
        textView.text = """RecipeFragment: recipeId = $recipeId"""
    }
}
