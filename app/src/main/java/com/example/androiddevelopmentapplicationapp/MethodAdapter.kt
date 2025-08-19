package com.example.androiddevelopmentapplicationapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplicationdevelopmentxml.databinding.ItemCookingStepBinding

class MethodAdapter(
    private val methodSteps: List<String>
) : RecyclerView.Adapter<MethodAdapter.MethodViewHolder>() {

    inner class MethodViewHolder(binding: ItemCookingStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
            val stepDescriptionTextView: TextView = binding.tvStepDescription
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MethodViewHolder {
        val binding = ItemCookingStepBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MethodViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MethodViewHolder,
        position: Int
    ) {
        val step = methodSteps[position]

        holder.stepDescriptionTextView.text = step
    }

    override fun getItemCount(): Int = methodSteps.size
}