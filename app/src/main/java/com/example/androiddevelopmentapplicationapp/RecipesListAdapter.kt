package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.databinding.ItemRecipeBinding

class RecipesListAdapter(
    private val recipes: List<Recipe>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            with(binding) {
                tvRecipeTitle.text = recipe.title

                try {
                    root.context.assets.open(recipe.imageUrl).use { inputStream ->
                        val drawable = Drawable.createFromStream(inputStream, null)
                        ivRecipeImage.setImageDrawable(drawable)
                    }
                } catch (e: Exception) {
                    Log.e("RecipesAdapter", "Error loading image: ${recipe.imageUrl}", e)
                    ivRecipeImage.setImageResource(android.R.color.darker_gray)
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(recipes[position].id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size
}