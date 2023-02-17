package com.android.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), OnClickListener {

    private var mUserName: String? = null
    private var mCorrectAnswers: Int = 0

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = -1


    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null

    private var tvOptions: ArrayList<TextView?> = arrayListOf(null, null, null, null)

    private var btnSubmit: Button? = null

    private val optionIds = arrayListOf(
        R.id.tv_option_one, R.id.tv_option_two, R.id.tv_option_three, R.id.tv_option_four
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.pb_progressbar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        btnSubmit = findViewById(R.id.btn_submit)


        optionIds.forEachIndexed { index, optionId ->
            tvOptions[index] = findViewById(optionId)
        }
        tvOptions.forEach { tv -> tv?.setOnClickListener(this) }
        btnSubmit?.setOnClickListener(this)

        mQuestionList = Constants.getQuestions()
        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionsView()
        val question = mQuestionList!!.get(mCurrentPosition - 1)
        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "${mCurrentPosition}/${progressBar?.max}"
        tvQuestion?.text = question.question

        question.options.forEachIndexed { index, option ->
            tvOptions[index]?.text = option
        }

        if (mCurrentPosition == mQuestionList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        tvOptions.forEach { tvOption ->
            run {
                tvOption?.setTextColor(Color.parseColor("#7A8089"))
                tvOption?.typeface = Typeface.DEFAULT
                tvOption?.background =
                    ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            }
        }
    }

    private fun selectOptionView(view: TextView, optionNumber: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = optionNumber
        view.setTextColor(Color.parseColor("#363A43"))
        view.typeface = Typeface.DEFAULT
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        view?.let {
            optionIds.forEachIndexed { index, optionId ->
                if (optionId == view?.id) {
                    selectOptionView(it as TextView, index)
                }
            }
            when (view.id) {
                R.id.btn_submit -> {
                    onSubmit()
                }
            }
        }
    }

    private fun onSubmit() {
        if (mSelectedOptionPosition == -1) {
            mCurrentPosition++
            if (mCurrentPosition <= mQuestionList!!.size) {
                setQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(Constants.USER_NAME, mUserName)
                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                startActivity(intent)
                finish()
                Toast.makeText(this, "You made it to the end", Toast.LENGTH_LONG).show()
            }
        } else {
            val question = mQuestionList?.get(mCurrentPosition - 1)
            if (mSelectedOptionPosition != question?.correctAnswer) {
                tvOptions[mSelectedOptionPosition]?.background =
                    ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
            } else {
                mCorrectAnswers++
            }
            tvOptions[question!!.correctAnswer]?.background =
                ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            if (mCurrentPosition == mQuestionList!!.size) {
                btnSubmit?.text = "FINISH"
            } else {
                btnSubmit?.text = "GO TO NEXT QUESTION"
            }
            mSelectedOptionPosition = -1
        }
    }
}