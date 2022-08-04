package com.berteek.geoquizz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class GeoQuizzViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val questions = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_middle_east, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentQuestionIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        private set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionCorrectAnswer: Boolean
        get() = questions[currentQuestionIndex].correctAnswer

    val currentQuestionText: Int
        get() = questions[currentQuestionIndex].textResId

    val currentQuestionUserAnswer: Boolean?
        get() = questions[currentQuestionIndex].userAnswer


    var cheatsLeft: Int
        get() = savedStateHandle.get(CHEATS_LEFT_KEY) ?: 3
        private set(value) = savedStateHandle.set(CHEATS_LEFT_KEY, value)

    val hasCheatedOnCurrentQuestion: Boolean
        get() = cheatedQuestions.contains(currentQuestionIndex)

    private var cheatedQuestions: HashSet<Int>
        get() = savedStateHandle.get(CHEATED_QUESTIONS_KEY) ?: initializeCheatedQuestions()
        set(value) = savedStateHandle.set(CHEATED_QUESTIONS_KEY, value)

    private fun initializeCheatedQuestions(): HashSet<Int> {
        cheatedQuestions = hashSetOf()
        return cheatedQuestions
    }

    fun markCurrentQuestionAsCheated() {
        cheatedQuestions.add(currentQuestionIndex)
        cheatsLeft--
    }


    fun nextQuestion() {
        if (currentQuestionIndex + 1 < questions.size) {
            currentQuestionIndex++
        }
    }

    fun previousQuestion() {
        if (currentQuestionIndex - 1 >= 0) {
            currentQuestionIndex--
        }
    }

    fun calculateCorrectAnswered(): Int {
        var correctAnswered = 0

        questions.forEach { question ->
            if (question.userAnswer == question.correctAnswer)
                correctAnswered++
        }

        return correctAnswered
    }

    fun checkIfAllAnswered(): Boolean {
        for (question in questions)
            if (question.userAnswer == null)
                return false
        return true
    }

    fun registerUserAnswer(answer: Boolean) {
        if (questions[currentQuestionIndex].userAnswer == null)
            questions[currentQuestionIndex].userAnswer = answer
    }


    companion object {
        private const val TAG = "GeoQuizzViewModel"

        const val CHEATS_LEFT_KEY = "CHEATS_LEFT_KEY"
        const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
        const val CHEATED_QUESTIONS_KEY = "CHEATED_QUESTIONS_KEY"
    }
}