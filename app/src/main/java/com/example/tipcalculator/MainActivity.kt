package com.example.tipcalculator

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) :
    InputFilter {
    var mPattern: Pattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }

}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        slider.setLabelFormatter(LabelFormatter { "${String.format("%.0f", slider.value)}%" })
        findViewById<EditText>(R.id.edit_text).doAfterTextChanged { text ->
            if (edit_text.text.isNotEmpty() && persons.text.isNotEmpty()) {
                edit_text.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(8, 2))
                val tip = text.toString().toDouble() * slider.value / 100
                val total = tip + text.toString().toDouble()
                val tipPerson = tip / persons.text.toString().toInt()
                val totalPerson = total / persons.text.toString().toInt()
                text_view.text = " Tip amount: $${String.format("%.2f", tip)}\n" +
                        " Total cost: $${String.format("%.2f", total)}\n" +
                        " Tip per person: $${String.format("%.2f", tipPerson)}\n" +
                        " Total per person: $${String.format("%.2f", totalPerson)}"
            } else text_view.text = ""
        }
        findViewById<Slider>(R.id.slider).addOnChangeListener { _, value, _ ->
            if (edit_text.text.isNotEmpty() && persons.text.isNotEmpty()) {
                val tip = edit_text.text.toString().toDouble() * value / 100
                val total = tip + edit_text.text.toString().toDouble()
                val tipPerson = tip / persons.text.toString().toInt()
                val totalPerson = total / persons.text.toString().toInt()
                text_view.text = " Tip amount: $${String.format("%.2f", tip)}\n" +
                        " Total cost: $${String.format("%.2f", total)}\n" +
                        " Tip per person: $${String.format("%.2f", tipPerson)}\n" +
                        " Total per person: $${String.format("%.2f", totalPerson)}"
            } else text_view.text = ""
        }
        findViewById<EditText>(R.id.persons).doAfterTextChanged { text ->
            if (edit_text.text.isNotEmpty() && persons.text.isNotEmpty()) {
                val tip = edit_text.text.toString().toDouble() * slider.value / 100
                val total = tip + edit_text.text.toString().toDouble()
                val tipPerson = tip / text.toString().toInt()
                val totalPerson = total / text.toString().toInt()
                text_view.text = " Tip amount: $${String.format("%.2f", tip)}\n" +
                        " Total cost: $${String.format("%.2f", total)}\n" +
                        " Tip per person: $${String.format("%.2f", tipPerson)}\n" +
                        " Total per person: $${String.format("%.2f", totalPerson)}"
            } else text_view.text = ""
        }
    }
}