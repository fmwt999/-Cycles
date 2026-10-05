package com.example.ycles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var xInput by remember { mutableStateOf("2.0") }
    var epsilonInput by remember { mutableStateOf("0.000001") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            text = "Вариант 2. Вычисление ряда",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "S = 1/x − 1/(3x³) + 1/(5x⁵) − 1/(7x⁷) + …",
            fontSize = 18.sp
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Введите x",
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = xInput,
            onValueChange = { newValue -> xInput = newValue },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Введите epsilon",
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = epsilonInput,
            onValueChange = { newValue -> epsilonInput = newValue },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = result,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
fun computeSeries(x: Double, epsilon: Double = 1e-6): Triple<Double, Double, Int> {
    var sum = 0.0
    var lastTerm = 0.0
    var iterations = 0

    var n = 0
    while (true) {
        val power = 2 * n + 1
        val denominator = power * x.pow(power)
        val term = 1.0 / denominator
        val signedTerm = if (n % 2 == 0) term else -term

        if (abs(signedTerm) < epsilon) {
            lastTerm = signedTerm
            break
        }
        sum += signedTerm
        lastTerm = signedTerm
        iterations++
        n++
    }
    return Triple(sum, lastTerm, iterations)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        DemoScreen()
    }
}