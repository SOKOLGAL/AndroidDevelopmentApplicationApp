package com.example.androiddevelopmentapplicationapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.databinding.ItemIngredientBinding
import java.math.RoundingMode
import java.math.BigDecimal

class IngredientsAdapter(
    private val baseIngredients: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var quantity: Int = 3

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemIngredientBinding.bind(itemView)

        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun bind(ingredient: Ingredient) {
            binding.tvIngredientName.text = ingredient.description

            val calculatedAmount = BigDecimal(ingredient.quantity)
                .multiply(BigDecimal.valueOf(quantity.toLong()))
                .divide(BigDecimal.valueOf(3))

            val formattedAmount = calculatedAmount
                .setScale(1, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()

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
            holder.bind(baseIngredients[position])
        }

        @SuppressLint("NotifyDataSetChanged")
        fun updateIngredients(progress: Int) {
            quantity = progress
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = baseIngredients.size

        private fun BigDecimal.isWhole(): Boolean {
            return this.remainder(BigDecimal.ONE) == BigDecimal.ZERO
        }
    }