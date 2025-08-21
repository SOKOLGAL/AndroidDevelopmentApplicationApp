package com.example.androiddevelopmentapplicationapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.databinding.ItemIngredientBinding

class IngredientsAdapter(
    private val baseIngredients: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var quantity: Int = 3
    private val ingredients = mutableListOf<Ingredient>()

    init {
        ingredients.addAll(baseIngredients)
    }

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemIngredientBinding.bind(itemView)

        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun bind(ingredient: Ingredient) {
            binding.tvIngredientName.text = ingredient.description

            val calculatedAmount = ingredient.amount * (quantity.toFloat() / 3)

            val formattedAmount = if (calculatedAmount.isWholeNumber()) {
                calculatedAmount.toInt().toString()
            } else {
                String.format("%.1f", calculatedAmount)
            }

            binding.tvIngredientName.text = "$formattedAmount ${ingredient.unitOfMeasure}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                com.example.androidapplicationdevelopmentxml.R.layout.item_ingredient,
                parent,
                false
            )
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: IngredientViewHolder,
        position: Int
    ) {
        holder.bind(ingredients[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(progress: Int) {
        quantity = progress + 1
        val updatedIngredients = baseIngredients.map { ingredient ->
            ingredient.copy(
                amount = ingredient.amount * (quantity.toFloat() / 3)
            )
        }
        ingredients.clear()
        ingredients.addAll(updatedIngredients)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = ingredients.size

    private fun Float.isWholeNumber(): Boolean {
        return this % 1f == 0f
    }
}