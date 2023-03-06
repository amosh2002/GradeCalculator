package com.example.hw2

import android.content.Context
import android.content.SharedPreferences
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

        val sharedPreferences: SharedPreferences = getSharedPreferences(
            getString(R.string.app_shared_prefs),
            Context.MODE_PRIVATE
        )
        sharedPreferences.let { sharedP ->
            val isFirstTimeOpening =
                sharedP.getBoolean(getString(R.string.first_time_opening), true)

            if (isFirstTimeOpening) {
                with(sharedP.edit()) {
                    putBoolean(getString(R.string.first_time_opening), false)
                    apply()
                }
            } else {
                addedHomeworksTextView.text = getString(R.string.added_homeworks_hint) +
                        sharedPreferences.getString(getString(R.string.homeworks), "")
                participationInputField.setText(
                    sharedPreferences.getString(getString(R.string.participation), "")
                )
                presentationInputField.setText(
                    sharedPreferences.getString(getString(R.string.group_presentation), "")
                )
                midterm1InputField.setText(
                    sharedPreferences.getString(getString(R.string.midterm1), "")
                )
                midterm2InputField.setText(
                    sharedPreferences.getString(getString(R.string.midterm2), "")
                )
                finalProjectInputField.setText(
                    sharedPreferences.getString(getString(R.string.final_project), "")
                )
                resultTextView.text =
                    sharedPreferences.getString(getString(R.string.final_grade), "")
            }
        }

        calculateButton.setOnClickListener {
            grades.setHomeworks(addedHomeworksTextView.text.toString().split(":")[1])
            grades.setParticipation(participationInputField.text.toString())
            grades.setPresentation(presentationInputField.text.toString())
            grades.setMidterm1(midterm1InputField.text.toString())
            grades.setMidterm2(midterm2InputField.text.toString())
            grades.setFinalProject(finalProjectInputField.text.toString())

            resultTextView.text = grades.calculateFinalGrade()
            saveToPreferences(sharedPreferences)
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
            if (addedHomeworksTextView.text.toString().split(":")[1] != "")
                addedHomeworksTextView.text = addedHomeworksTextView.text.toString() + ","

            addedHomeworksTextView.text =
                addedHomeworksTextView.text.toString() + homeworksInputField.text.toString()

            homeworksInputField.setText("")
        }

        resetHWsButton.setOnClickListener {
            addedHomeworksTextView.text = getString(R.string.added_homeworks_hint)
            homeworksInputField.setText("")
            flag = 0
        }

        resetButton.setOnClickListener {
            addedHomeworksTextView.text = getString(R.string.added_homeworks_hint)
            homeworksInputField.setText("")
            participationInputField.setText("")
            presentationInputField.setText("")
            midterm1InputField.setText("")
            midterm2InputField.setText("")
            finalProjectInputField.setText("")
            resultTextView.text = getString(R.string.final_grade_empty_text)
        }
    }

    private fun saveToPreferences(sharedPreferences: SharedPreferences) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(getString(R.string.homeworks), grades.getHomeworks())
        editor.putString(getString(R.string.participation), grades.getParticipation())
        editor.putString(
            getString(R.string.group_presentation),
            grades.getPresentation()
        )
        editor.putString(getString(R.string.midterm1), grades.getMidterm1())
        editor.putString(getString(R.string.midterm2), grades.getMidterm2())
        editor.putString(getString(R.string.final_project), grades.getFinalProject())
        editor.putString(getString(R.string.final_grade), grades.calculateFinalGrade())
        editor.apply()
        editor.commit()
    }
}