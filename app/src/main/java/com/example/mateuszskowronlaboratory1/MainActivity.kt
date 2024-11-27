package com.example.mateuszskowronlaboratory1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mateuszskowronlaboratory1.ui.theme.MateuszSkowronLaboratory1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val ctx = LocalContext.current

            var weight: MutableState<String> = remember { mutableStateOf("") }
            var height: MutableState<String> = remember { mutableStateOf("") }
            var bmiResult: MutableState<Double?> = remember { mutableStateOf(null) }
            var diagnosis: MutableState<String> = remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(20.dp, 40.dp, 20.dp, 0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = weight.value,
                    onValueChange = { weight.value = it },
                    label = { Text("Type your weight in KG") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                TextField(
                    value = height.value,
                    onValueChange = { height.value = it },
                    label = { Text("Type your height in M") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Button(
                    onClick = {
                        try {
                            val w = weight.value.toDouble()
                            if (w <= 0) {
                                Toast.makeText(ctx, "Weight must be greater than 0", LENGTH_LONG).show()
                                return@Button
                            }

                            val h = height.value.toDouble()

                            if (h <= 0) {
                                Toast.makeText(ctx, "Height must be greater than 0", LENGTH_LONG).show()
                                return@Button
                            }

                            val bmi = w / (h * h)
                            bmiResult.value = bmi

                            diagnosis.value = when {
                                bmi < 18.5 -> "Underweight"
                                bmi <= 24.9 -> "Healthy"
                                bmi <= 29.9 -> "Overweight"
                                else -> "Obesity"
                            }

                            Log.i("BMICalculator", "BMI: $bmi, Diagnosis: ${diagnosis.value}")
                        } catch (e: NumberFormatException) {
                            Toast.makeText(ctx, "Please enter valid numbers", LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text("Calculate my BMI")
                }

                bmiResult.value?.let { bmi ->
                    Text(
                        "Your BMI: %.2f".format(bmi),
                        fontSize = 24.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        diagnosis.value,
                        fontSize = 24.sp,
                        color = when(diagnosis.value) {
                            "Underweight" -> Color.Blue
                            "Healthy" -> Color.Green
                            "Overweight" -> Color.Yellow
                            "Obesity" -> Color.Red
                            else -> Color.Black
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Display corresponding image
                    val imageRes = when(diagnosis.value) {
                        "Underweight" -> R.drawable.underweight
                        "Healthy" -> R.drawable.normal
                        "Overweight" -> R.drawable.overweight
                        "Obesity" -> R.drawable.obesity
                        else -> null
                    }

                    imageRes?.let {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = "BMI Status Image",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}