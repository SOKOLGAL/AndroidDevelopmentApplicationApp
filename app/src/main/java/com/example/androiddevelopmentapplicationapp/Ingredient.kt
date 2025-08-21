package com.example.androiddevelopmentapplicationapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val quantity: Int,
    val unitOfMeasure: String,
    val description: String,
) : Parcelable