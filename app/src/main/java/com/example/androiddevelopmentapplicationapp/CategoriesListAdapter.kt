package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.R
import com.example.androiddevelopmentapplicationapp.com.example.androiddevelopmentapplicationapp.Category

class CategoriesListAdapter(
    private val dataset: List<Category>
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivCategoryImage)
        val titleTextView: TextView = view.findViewById(R.id.tvCategoryTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.tvCategoryDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = dataset[position]
        holder.titleTextView.text = category.title as CharSequence?
        holder.descriptionTextView.text = category.description

        val drawable: Drawable? = try {
            holder.imageView.context.assets.open(category.imageUrl).use { inputStream ->
                Drawable.createFromStream(inputStream, null)
            }
        } catch (e: Exception) {
            Log.d("CategoriesListAdapter", "Image not found: ${category.imageUrl}")
            null
        }
        holder.imageView.setImageDrawable(drawable)
    }

    override fun getItemCount(): Int = dataset.size
}
