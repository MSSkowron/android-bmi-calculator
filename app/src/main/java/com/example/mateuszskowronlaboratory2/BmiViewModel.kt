package com.example.mateuszskowronlaboratory2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {
    var weight = MutableLiveData<String>()
    var height = MutableLiveData<String>()

    fun calculateBmi(): Pair<Float, String>? {
        val w = weight.value?.toFloatOrNull()
        val h = height.value?.toFloatOrNull()

        if (w == null || h == null || h <= 0) {
            return null
        }

        val bmi = w / (h * h)
        val diagnosis = when {
            bmi < 18.5f -> "Underweight"
            bmi <= 24.9f -> "Healthy"
            bmi <= 29.9f -> "Overweight"
            else -> "Obesity"
        }

        return Pair(bmi, diagnosis)
    }
}