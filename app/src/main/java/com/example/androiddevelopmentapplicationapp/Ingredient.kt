package com.example.androiddevelopmentapplicationapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
    val amount: Float
) : Parcelable