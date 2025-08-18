package com.example.androiddevelopmentapplicationapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.databinding.ItemIngredientBinding

class IngredientsAdapter(
    private val dataset: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val quantityTextView: TextView = binding.tvIngredientQuantity
        val nameTextView: TextView = binding.tvIngredientName
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: IngredientViewHolder,
        position: Int
    ) {
        val ingredient = dataset[position]

        val quantityText = "${ingredient.quantity} ${ingredient.unitOfMeasure}"

        holder.quantityTextView.text = quantityText
        holder.nameTextView.text = ingredient.description
    }

    override fun getItemCount(): Int = dataset.size
}