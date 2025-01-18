package ru.taxcom.feedback.ui

import androidx.lifecycle.ViewModel
import ru.taxcom.feedback.storage.FeedbackCountPreferences
import javax.inject.Inject

class FeedbackScreenViewModel @Inject constructor(
    feedbackCountPreferences: FeedbackCountPreferences
) : ViewModel() {

    val feedbackCount = feedbackCountPreferences.getFeedbackCount()
}