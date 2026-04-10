package com.example.mycalc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )

        val operatorIds = listOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        )

        // Number buttons
        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                display.append((it as Button).text)
                canAddOperation = true
            }
        }

        // Operator buttons (emoji)
        operatorIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                if (canAddOperation) {
                    display.append((it as Button).text)
                    canAddOperation = false
                    canAddDecimal = true
                }
            }
        }

        // Clear button 🩷
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            display.text = ""
            canAddOperation = false
            canAddDecimal = true
        }

        // Decimal
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            if (canAddDecimal) {
                display.append(".")
                canAddDecimal = false
            }
        }

        // Equals 🟰
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            if (display.text.isNotEmpty()) {
                try {
                    var expressionText = display.text.toString()
                    // Replace emoji operators with math symbols
                    expressionText = expressionText.replace("➕", "+")
                        .replace("➖", "-")
                        .replace("✖️", "*")
                        .replace("➗", "/")

                    val expression = ExpressionBuilder(expressionText).build()
                    val result = expression.evaluate()

                    // Show integer without decimal if possible
                    display.text = if (result % 1.0 == 0.0) {
                        result.toInt().toString()
                    } else {
                        result.toString()
                    }
                } catch (e: Exception) {
                    display.text = "Error"
                }
            }
        }
    }
}