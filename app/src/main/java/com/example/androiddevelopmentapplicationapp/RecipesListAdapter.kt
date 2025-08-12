package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.databinding.ItemRecipeBinding

class RecipesListAdapter(
    private val recipes: List<Recipe>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
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
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.tvRecipeTitle.text = recipe.title

        try {
            holder.binding.root.context.assets.open(recipe.imageUrl).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                holder.binding.ivRecipeImage.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            holder.binding.ivRecipeImage.setImageResource(android.R.color.darker_gray)
        }
    }

    override fun getItemCount(): Int = recipes.size
}