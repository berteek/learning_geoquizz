package com.berteek.geoquizz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    var isShowingAnswer: Boolean
        get() = savedStateHandle.get(CheatActivity.IS_SHOWING_ANSWER_KEY) ?: false
        set(value) = savedStateHandle.set(CheatActivity.IS_SHOWING_ANSWER_KEY, value)
}