package com.berteek.geoquizz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    var isShowingAnswer: Boolean
        get() = savedStateHandle.get(IS_SHOWING_ANSWER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_SHOWING_ANSWER_KEY, value)

    companion object {
        const val IS_SHOWING_ANSWER_KEY = "IS_SHOWING_ANSWER_KEY"
    }
}