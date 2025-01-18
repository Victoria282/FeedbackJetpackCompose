package ru.taxcom.feedback.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class FeedbackCountPreferences @Inject constructor(context: Context) {
    private val sharedPrefs =
        context.getSharedPreferences(FEEDBACK_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveFeedbackCount(count: Int) {
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.putInt(FEEDBACK_COUNT_PREFERENCE_KEY, count)
        editor.apply()
    }

    fun getFeedbackCount(): Int =
        sharedPrefs.getInt(FEEDBACK_COUNT_PREFERENCE_KEY, 0)

    companion object {
        private const val FEEDBACK_COUNT_PREFERENCE_KEY = "FEEDBACK_COUNT_PREFERENCE_KEY"
        private const val FEEDBACK_PREFERENCE_NAME = "FEEDBACK_PREFERENCE_NAME"
    }
}