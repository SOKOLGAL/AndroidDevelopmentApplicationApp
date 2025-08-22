package com.example.androiddevelopmentapplicationapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("FragmentRecipeBinding is null!")

    private lateinit var recipe: Recipe
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var methodAdapter: MethodAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.ARG_RECIPE, Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(Constants.ARG_RECIPE)
        } ?: throw IllegalArgumentException("Recipe must be provided")

        initUI()
        initRecyclers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initUI() {
        binding.tvRecipeTitle.text = recipe.title
        try {
            requireContext().assets.open(recipe.imageUrl).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivRecipeImage.setImageDrawable(drawable)
                binding.ivRecipeImage.contentDescription = "Фотография блюда: ${recipe.title}"
            }
        } catch (e: Exception) {
            Log.e("RecipeFragment", "Error loading image", e)
            binding.ivRecipeImage.setImageResource(R.drawable.bcg_recipes_list)
            binding.ivRecipeImage.contentDescription = getString(
                R.string.recipe_image_default_dish
            )
        }
    }

    private fun initRecyclers() {
        ingredientsAdapter = IngredientsAdapter(recipe.ingredients)
        val ingredientsDivider = createMaterialDivider()
        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ingredientsDivider)
        }
        initPortionsSeekBar()
        methodAdapter = MethodAdapter(recipe.method)
        val methodStepsDivider = createMaterialDivider()

        binding.rvMethod.apply {
            adapter = methodAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(methodStepsDivider)
        }
    }

    private fun createMaterialDivider(): MaterialDividerItemDecoration {
        return MaterialDividerItemDecoration(
            requireContext(),
            MaterialDividerItemDecoration.VERTICAL
        ).apply {
            dividerColor = ContextCompat.getColor(requireContext(), R.color.dividerColor)
            dividerThickness = resources.getDimensionPixelSize(R.dimen.divider_thickness_1)
            dividerInsetStart = resources.getDimensionPixelSize(R.dimen.main_padding_indent_12)
            dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.main_padding_indent_12)
            isLastItemDecorated = false
        }
    }

    private fun initPortionsSeekBar() {
        binding.sbPortions.apply {
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val portions = progress + 1
                    binding.tvNumberOfServings.text = portions.toString()
                    ingredientsAdapter.updateIngredients(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}