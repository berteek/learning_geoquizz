package com.berteek.geoquizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.berteek.geoquizz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questions = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_middle_east, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentQuestionIndex = 0

    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateQuestion()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    private fun checkAnswer(answer: Boolean) {
        if (questions[currentQuestionIndex].userAnswer != null)
            return

        questions[currentQuestionIndex].userAnswer = answer

        val message =
            if (questions[currentQuestionIndex].correctAnswer == questions[currentQuestionIndex].userAnswer)
                R.string.correct_message
            else
                R.string.incorrect_message

        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun updateQuestion() {
        binding.questionText.setText(questions[currentQuestionIndex].textResId)

        if (questions[currentQuestionIndex].userAnswer == null)
            setButtonsIsEnabled(true)
        else
            setButtonsIsEnabled(false)
    }

    private fun setButtonsIsEnabled(isEnabled: Boolean) {
        binding.trueButton.isEnabled = isEnabled
        binding.falseButton.isEnabled = isEnabled
    }

    private fun setupListeners() {
        binding.trueButton.setOnClickListener {
            registerUserAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            registerUserAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            nextQuestion()
        }

        binding.previousButton.setOnClickListener {
            previousQuestion()
        }

        binding.questionText.setOnClickListener {
            nextQuestion()
        }
    }

    private fun registerUserAnswer(answer: Boolean) {
        checkAnswer(answer)
        updateQuestion()
        val areAllAnswered = checkIfAllAnswered()
        if (areAllAnswered)
            Snackbar.make(
                binding.root,
                getString(R.string.score_message, calculateCorrectAnswered(), questions.size),
                Snackbar.LENGTH_SHORT
            ).show()
    }

    private fun checkIfAllAnswered(): Boolean {
        for (question in questions)
            if (question.userAnswer == null)
                return false
        return true
    }

    private fun calculateCorrectAnsweredPercentage(): Float {
        var correctAnswered = 0

        for (question in questions)
            if (question.userAnswer == question.correctAnswer)
                correctAnswered++

        return correctAnswered.toFloat() / questions.size
    }

    private fun calculateCorrectAnswered(): Int {
        var correctAnswered = 0

        for (question in questions)
            if (question.userAnswer == question.correctAnswer)
                correctAnswered++

        return correctAnswered
    }

    private fun nextQuestion() {
        if (currentQuestionIndex + 1 < questions.size) {
            currentQuestionIndex++
            updateQuestion()
        }
    }

    private fun previousQuestion() {
        if (currentQuestionIndex - 1 >= 0) {
            currentQuestionIndex--
            updateQuestion()
        }
    }
}