package com.berteek.geoquizz

import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int,
    val correctAnswer: Boolean
) {
    var userAnswer: Boolean? = null
}
