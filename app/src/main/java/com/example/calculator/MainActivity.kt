package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Calculate()
            }
        }
    }
}

@Composable
fun Calculate(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("0") }
    var operator by remember { mutableStateOf<String?>(null) }

    fun onButtonClick(value: String) {
        when {
            value.isNumeric() -> {
                if (inputValue == "0" || inputValue == "-0") {
                    inputValue = value
                } else {
                    inputValue += value
                }
            }
            value == "," && "." !in inputValue -> {
                inputValue += "."
            }
            value == "+/-" -> {
                inputValue = if (inputValue.startsWith("-")) {
                    inputValue.substring(1)
                } else {
                    "-$inputValue"
                }
            }
            value in listOf("+", "-", "x", "÷") -> {
                operator = value
                result = inputValue
                inputValue = "0"
            }
            value == "=" -> {
                if (operator != null) {
                    result = performCalculation(result.toDouble(), inputValue.toDouble(), operator!!)
                    inputValue = result
                    operator = null
                }
            }
            value == "C" -> {
                inputValue = "0"
                result = "0"
                operator = null
            }
            value == "<--" -> {
                inputValue = if (inputValue.length > 1) {
                    inputValue.dropLast(1)
                } else {
                    "0"
                }
            }
            value == "%" -> {
                if (inputValue != "0") {
                    inputValue = (inputValue.toDouble() / 100).toString()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = inputValue,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            textAlign = TextAlign.End
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonElement(text = "%") {onButtonClick("%")}
            ButtonElement(text = "C") { onButtonClick("C") }
            ButtonElement(text = "<--") {onButtonClick("<--")}
            ButtonElement(text = "+") { onButtonClick("+") }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonElement(text = "1") { onButtonClick("1") }
            ButtonElement(text = "2") { onButtonClick("2") }
            ButtonElement(text = "3") { onButtonClick("3") }
            ButtonElement(text = "-") { onButtonClick("-") }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonElement(text = "4") { onButtonClick("4") }
            ButtonElement(text = "5") { onButtonClick("5") }
            ButtonElement(text = "6") { onButtonClick("6") }
            ButtonElement(text = "x") { onButtonClick("x") }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonElement(text = "7") { onButtonClick("7") }
            ButtonElement(text = "8") { onButtonClick("8") }
            ButtonElement(text = "9") { onButtonClick("9") }
            ButtonElement(text = "÷") { onButtonClick("÷") }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonElement(text = "+/-") { onButtonClick("+/-") }
            ButtonElement(text = "0") { onButtonClick("0") }
            ButtonElement(text = ",") { onButtonClick(",") }
            ButtonElement(text = "=") { onButtonClick("=") }
        }
    }
}

@Composable
fun ButtonElement(text: String, onButtonClick: (String) -> Unit = {}) {
    Button(
        onClick = { onButtonClick(text) },
        modifier = Modifier
            .width(80.dp)
            .height(80.dp)
    ) {
        Text(
            text = text,
            fontSize = 24.sp
        )
    }
}

fun performCalculation(firstNumber: Double, secondNumber: Double, operator: String): String {
    return when (operator) {
        "+" -> (firstNumber + secondNumber).toString()
        "-" -> (firstNumber - secondNumber).toString()
        "x" -> (firstNumber * secondNumber).toString()
        "÷" -> if (secondNumber != 0.0) (firstNumber / secondNumber).toString() else "Ошибка"
        else -> "Ошибка"
    }
}

fun String.isNumeric(): Boolean {
    return try {
        this.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
