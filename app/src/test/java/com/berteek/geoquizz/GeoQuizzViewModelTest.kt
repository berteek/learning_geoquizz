package com.berteek.geoquizz

import androidx.lifecycle.SavedStateHandle
import com.berteek.geoquizz.GeoQuizzViewModel.Companion.CURRENT_INDEX_KEY
import org.junit.Assert.*
import org.junit.Test

class GeoQuizzViewModelTest {

    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val viewModel = GeoQuizzViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, viewModel.currentQuestionText)
    }

    @Test
    fun stopsAfterLastQuestion() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val viewModel = GeoQuizzViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, viewModel.currentQuestionText)
        viewModel.nextQuestion()
        assertEquals(R.string.question_asia, viewModel.currentQuestionText)
    }

    @Test
    fun stopsBeforeFirstQuestion() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 0))
        val viewModel = GeoQuizzViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, viewModel.currentQuestionText)
        viewModel.previousQuestion()
        assertEquals(R.string.question_australia, viewModel.currentQuestionText)
    }

    @Test
    fun verifyCorrectAnswers() {
        val savedStateHandle = SavedStateHandle()
        val viewModel = GeoQuizzViewModel(savedStateHandle)
        val correctAnswers = listOf(true, true, false, false, true, true)
        correctAnswers.forEach {
            if (it)
                assertTrue(viewModel.currentQuestionCorrectAnswer)
            else
                assertFalse(viewModel.currentQuestionCorrectAnswer)
            viewModel.nextQuestion()
        }
    }
}
