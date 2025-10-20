package hust.test2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true

    private lateinit var workingsTV: TextView
    private lateinit var resultsTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lấy đúng ID của TextView
        workingsTV = findViewById(R.id.workingsTV)
        resultsTV = findViewById(R.id.resultsTV)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal) {
                    workingsTV.text = workingsTV.text.toString() + view.text
                    canAddDecimal = false
                }
            } else {
                workingsTV.text = workingsTV.text.toString() + view.text
            }
            canAddOperation = true
        }
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            workingsTV.text = workingsTV.text.toString() + view.text
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View) {
        workingsTV.text = ""
        resultsTV.text = ""
        canAddDecimal = true
        canAddOperation = false
    }

    fun backSpaceAction(view: View) {
        val length = workingsTV.text.length
        if (length > 0) {
            workingsTV.text = workingsTV.text.subSequence(0, length - 1)
        }
    }

    fun equalsAction(view: View) {
        resultsTV.text = calculateResults()
    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in 1 until passedList.size step 2) {
            val operator = passedList[i] as Char
            val nextDigit = passedList[i + 1] as Float
            when (operator) {
                '+' -> result += nextDigit
                '-' -> result -= nextDigit
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        val newList = passedList.toMutableList()
        var i = 0
        while (i < newList.size) {
            if (newList[i] is Char && i > 0 && i < newList.lastIndex) {
                val operator = newList[i] as Char
                if (operator == 'x' || operator == '/') {
                    val prevDigit = newList[i - 1] as Float
                    val nextDigit = newList[i + 1] as Float
                    val result = if (operator == 'x') prevDigit * nextDigit else prevDigit / nextDigit

                    newList[i - 1] = result
                    newList.removeAt(i)
                    newList.removeAt(i)

                    i -= 1
                }
            }
            i += 1
        }
        return newList
    }

    private fun digitsOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""

        for (character in workingsTV.text) {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else if (character in listOf('+', '-', 'x', '/')) {
                if (currentDigit.isNotEmpty()) {
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                }
                list.add(character)
            }
        }

        if (currentDigit.isNotEmpty()) {
            list.add(currentDigit.toFloat())
        }

        return list
    }
}
