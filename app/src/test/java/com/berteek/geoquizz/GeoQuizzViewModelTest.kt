package com.berteek.geoquizz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class GeoQuizzViewModelTest {

    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val geoQuizzViewModel = GeoQuizzViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, geoQuizzViewModel.currentQuestionText)
    }
}