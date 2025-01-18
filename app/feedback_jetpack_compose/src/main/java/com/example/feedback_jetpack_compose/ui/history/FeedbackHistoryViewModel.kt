package ru.taxcom.feedback.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.taxcom.feedback.R
import ru.taxcom.feedback.data.FeedbackHistoryItem
import ru.taxcom.feedback.storage.FeedbackCountPreferences
import ru.taxcom.feedback.usecase.GetFeedbackHistoryUseCase
import ru.taxcom.taxcomkit.data.resource.Resource
import ru.taxcom.taxcomkit.utils.resource.ResourceProvider
import javax.inject.Inject

class FeedbackHistoryViewModel @Inject constructor(
    private val feedbackCountPreferences: FeedbackCountPreferences,
    private val getFeedbackHistory: GetFeedbackHistoryUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _feedbackHistory =
        MutableStateFlow<Resource<List<FeedbackHistoryItem>>>(Resource.loading())
    val feedbackHistory: StateFlow<Resource<List<FeedbackHistoryItem>>> = _feedbackHistory

    init {
        getHistory()
    }

    private fun getHistory() = viewModelScope.launch {
        _feedbackHistory.emit(Resource.loading())
        try {
            val history = getFeedbackHistory.getHistory()
            val feedbackCount = history.data?.count { it.readed == false }
            feedbackCountPreferences.saveFeedbackCount(feedbackCount ?: 0)
            _feedbackHistory.emit(history)
        } catch (e: Exception) {
            _feedbackHistory.emit(
                Resource.error(
                    messageTitle = resourceProvider.getString(R.string.error_title),
                    message = resourceProvider.getString(R.string.feedback_error_connection)
                )
            )
        }
    }
}