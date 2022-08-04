package com.berteek.geoquizz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.berteek.geoquizz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private val viewModel: CheatViewModel by viewModels()

    private var answer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        if (viewModel.isShowingAnswer) {
            showAnswer()
        }

        binding.showAnswerButton.setOnClickListener {
            showAnswer()
        }

        binding.apiLevelText.text = getString(R.string.api_level, Build.VERSION.SDK_INT)
    }

    private fun showAnswer() {
        viewModel.isShowingAnswer = true
        binding.answerText.setText(if (answer) R.string.true_button else R.string.false_button)
        setAnswerShownResult()
    }

    private fun setAnswerShownResult() {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answer: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER, answer)
            }
        }

        const val EXTRA_ANSWER = "com.berteek.android.geoquizz.answer"
        const val EXTRA_ANSWER_SHOWN = "com.berteek.android.geoquizz.answer_shown"
    }
}