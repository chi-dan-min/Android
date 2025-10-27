package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private var currentInput: String = "0"
    private var lastNumber: Double = 0.0
    private var currentOperator: String = ""
    private var operatorClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Đặt bố cục đã tạo

        displayTextView = findViewById(R.id.display_text_view)

        // Gán listener cho các nút (cần phải tìm tất cả các nút và gán thủ công hoặc dùng vòng lặp)
        // Đây là một cách đơn giản để minh họa, bạn có thể cần tối ưu hóa bằng cách duyệt qua view group
        val buttonIds = listOf(
            R.id.button_ce, R.id.button_c, R.id.button_bs, R.id.button_divide,
            R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_multiply,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_subtract,
            R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_add,
            R.id.button_plus_minus, R.id.button_0, R.id.button_dot, R.id.button_equals
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { v ->
                val button = v as Button
                onButtonClick(button.text.toString())
            }
        }
    }

    private fun onButtonClick(text: String) {
        when (text) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> onNumberClick(text)
            "." -> onDecimalClick()
            "CE" -> onClearEntry()
            "C" -> onClearAll()
            "BS" -> onBackspace()
            "+", "-", "x", "/" -> onOperatorClick(text)
            "=" -> onEqualsClick()
            "+/-" -> onPlusMinusClick()
        }
        displayTextView.text = currentInput
    }

    private fun onNumberClick(number: String) {
        if (operatorClicked) {
            currentInput = number
            operatorClicked = false
        } else if (currentInput == "0") {
            currentInput = number
        } else {
            currentInput += number
        }
    }

    private fun onDecimalClick() {
        if (!currentInput.contains(".")) {
            currentInput += "."
        }
    }

    private fun onClearEntry() {
        currentInput = "0"
    }

    private fun onClearAll() {
        currentInput = "0"
        lastNumber = 0.0
        currentOperator = ""
        operatorClicked = false
    }

    private fun onBackspace() {
        if (currentInput.length > 1) {
            currentInput = currentInput.dropLast(1)
        } else {
            currentInput = "0"
        }
    }

    private fun onOperatorClick(operator: String) {
        if (!operatorClicked) {
            calculate()
            lastNumber = currentInput.toDouble()
        }
        currentOperator = operator
        operatorClicked = true
    }

    private fun onEqualsClick() {
        calculate()
        currentOperator = ""
        operatorClicked = false
    }

    private fun onPlusMinusClick() {
        currentInput = (currentInput.toDouble() * -1).toString()
        // Loại bỏ phần thập phân nếu kết quả là số nguyên
        if (currentInput.endsWith(".0")) {
            currentInput = currentInput.dropLast(2)
        }
    }

    private fun calculate() {
        if (currentOperator.isNotEmpty() && !operatorClicked) {
            val secondNumber = currentInput.toDouble()
            var result = 0.0
            when (currentOperator) {
                "+" -> result = lastNumber + secondNumber
                "-" -> result = lastNumber - secondNumber
                "x" -> result = lastNumber * secondNumber
                "/" -> {
                    if (secondNumber != 0.0) {
                        result = lastNumber / secondNumber
                    } else {
                        currentInput = "Error"
                        lastNumber = 0.0
                        return
                    }
                }
            }
            currentInput = result.toString()
            // Loại bỏ phần thập phân nếu kết quả là số nguyên
            if (currentInput.endsWith(".0")) {
                currentInput = currentInput.dropLast(2)
            }
            lastNumber = result
        }
    }
}