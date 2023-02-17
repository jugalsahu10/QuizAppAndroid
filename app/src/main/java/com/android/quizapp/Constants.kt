package com.android.quizapp

import java.util.*

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    private const val QUESTION = "What country does this flag belong to?"

    fun getQuestions(): ArrayList<Question> {
        val countryFlags: ArrayList<Int> = arrayListOf(
            R.drawable.ic_flag_of_argentina,
            R.drawable.ic_flag_of_australia,
            R.drawable.ic_flag_of_belgium,
            R.drawable.ic_flag_of_brazil,
            R.drawable.ic_flag_of_denmark,
            R.drawable.ic_flag_of_fiji,
            R.drawable.ic_flag_of_germany,
            R.drawable.ic_flag_of_india,
            R.drawable.ic_flag_of_kuwait,
            R.drawable.ic_flag_of_new_zealand,
        )
        val countries: ArrayList<String> = arrayListOf(
            "argentina",
            "australia",
            "belgium",
            "brazil",
            "denmark",
            "fiji",
            "germany",
            "india",
            "kuwait",
            "zealand",
        )

        val questions = arrayListOf<Question>()
        countryFlags.forEachIndexed { index, flag ->
            run {
                val options =
                    (0..9).filter { i -> i != index }.shuffled().take(3)
                        .map { i -> countries[i] }.toList()
                val arrayListOption = ArrayList(options)
                arrayListOption.add(countries[index])
                val randomShuffleNumber = (1..3).shuffled().take(1)[0]
                Collections.rotate(arrayListOption, -randomShuffleNumber)
                questions.add(
                    Question(
                        index,
                        QUESTION,
                        countryFlags[index],
                        arrayListOption,
                        3 - randomShuffleNumber
                    )
                )
            }
        }

        return questions
    }
}

fun main() {
    for (question in Constants.getQuestions()) {

        println("${question}")
    }
}