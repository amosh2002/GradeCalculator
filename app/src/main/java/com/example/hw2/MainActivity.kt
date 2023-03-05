package com.example.hw2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    var grades = GradesModel()
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultTextView: TextView = findViewById(R.id.text_view_result)
        val addedHomeworksTextView: TextView = findViewById(R.id.text_added_homeworks)

        val homeworksInputField: TextInputEditText = findViewById(R.id.text_input_field_homeworks)
        val participationInputField: TextInputEditText =
            findViewById(R.id.text_input_field_participation)
        val presentationInputField: TextInputEditText =
            findViewById(R.id.text_input_field_presentation)
        val midterm1InputField: TextInputEditText = findViewById(R.id.text_input_field_midterm1)
        val midterm2InputField: TextInputEditText = findViewById(R.id.text_input_field_midterm2)
        val finalProjectInputField: TextInputEditText =
            findViewById(R.id.text_input_field_final_project)

        val addHWButton: Button = findViewById(R.id.button_add_homework)
        val resetHWsButton: Button = findViewById(R.id.button_reset_homeworks)
        val calculateButton: Button = findViewById(R.id.button_calculate)
        val resetButton: Button = findViewById(R.id.button_reset)

        addHWButton.isEnabled = false

        calculateButton.setOnClickListener {
            grades.setHomeworks(addedHomeworksTextView.text.toString().split(": ")[1])
            grades.setParticipation(participationInputField.text.toString())
            grades.setPresentation(presentationInputField.text.toString())
            grades.setMidterm1(midterm1InputField.text.toString())
            grades.setMidterm2(midterm2InputField.text.toString())
            grades.setFinalProject(finalProjectInputField.text.toString())

            resultTextView.text = grades.calculateFinalGrade()
        }

        homeworksInputField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addHWButton.isEnabled = homeworksInputField.text.toString().isNotEmpty()
                if (flag == 1) {
                    addHWButton.isEnabled = false
                }
            }
        })

        addHWButton.setOnClickListener {
            if (addedHomeworksTextView.text.toString().count { it == ',' } == 3) {
                addHWButton.isEnabled = false
                flag = 1
            }
            if (addedHomeworksTextView.text.toString().split(":")[1] != " ")
                addedHomeworksTextView.text = addedHomeworksTextView.text.toString() + ","

            addedHomeworksTextView.text =
                addedHomeworksTextView.text.toString() + homeworksInputField.text.toString()

            homeworksInputField.setText("")
        }

        resetHWsButton.setOnClickListener {
            addedHomeworksTextView.text = "Added homeworks: "
            homeworksInputField.setText("")
        }

        resetButton.setOnClickListener {
            addedHomeworksTextView.text = "Added homeworks: "
            homeworksInputField.setText("")
            participationInputField.setText("")
            presentationInputField.setText("")
            midterm1InputField.setText("")
            midterm2InputField.setText("")
            finalProjectInputField.setText("")
            resultTextView.text = "Final grade will be displayed here"
        }
    }
}