package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.databinding.ItemCategoryBinding
import com.example.androiddevelopmentapplicationapp.com.example.androiddevelopmentapplicationapp.Category
import kotlin.with

class CategoriesListAdapter(
    private val dataset: List<Category>
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivCategoryImage: ImageView = binding.ivCategoryImage
        val tvCategoryTitle: TextView = binding.tvCategoryTitle
        val tvCategoryDescription: TextView = binding.tvCategoryDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = dataset[position]
        with(holder) {
            tvCategoryTitle.text = category.title
            tvCategoryDescription.text = category.description

            val drawable: Drawable? = try {
                itemView.context.assets.open(category.imageUrl).use { inputStream ->
                    Drawable.createFromStream(inputStream, null)
                }
            } catch (e: Exception) {
                Log.d("CategoriesListAdapter", "Image not found: ${category.imageUrl}")
                null
            }
            ivCategoryImage.setImageDrawable(drawable)
        }
    }

    override fun getItemCount(): Int = dataset.size
}