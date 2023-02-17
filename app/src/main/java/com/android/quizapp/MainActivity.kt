package com.android.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var btnStart: Button? = null
    private var etName: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btn_start)
        etName = findViewById(R.id.et_name)
        btnStart?.setOnClickListener {
            etName?.text?.let {
                if (it.isEmpty()) {
                    Toast.makeText(this, "Please enter your name!", Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(this, QuizQuestionsActivity::class.java)
                    intent.putExtra(Constants.USER_NAME, etName?.text.toString())
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}