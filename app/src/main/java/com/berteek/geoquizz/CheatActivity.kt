package com.berteek.geoquizz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.berteek.geoquizz.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER = "com.berteek.android.geoquizz.answer"
const val EXTRA_ANSWER_SHOWN = "com.berteek.android.geoquizz.answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private var answer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        binding.showAnswerButton.setOnClickListener {
            binding.answerText.setText(if (answer) R.string.true_button else R.string.false_button)
            setAnswerShownResult()
        }
    }

    private fun setAnswerShownResult() {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        }
        setResult(Activity.RESULT_CANCELED, resultIntent)
    }

    companion object {
        fun newIntent(packageContext: Context, answer: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER, answer)
            }
        }
    }
}