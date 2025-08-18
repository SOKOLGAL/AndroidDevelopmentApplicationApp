package com.example.androiddevelopmentapplicationapp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MethodAdapter (
    private val dataset: List<Recipe>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientsAdapter.IngredientViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: IngredientsAdapter.IngredientViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}