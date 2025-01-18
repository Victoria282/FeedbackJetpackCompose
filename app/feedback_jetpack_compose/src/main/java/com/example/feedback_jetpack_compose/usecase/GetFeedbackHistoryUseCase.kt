package ru.taxcom.feedback.usecase

import ru.taxcom.feedback.data.FeedbackHistoryItem
import ru.taxcom.taxcomkit.data.resource.Resource

interface GetFeedbackHistoryUseCase {
    suspend fun getHistory(): Resource<List<FeedbackHistoryItem>>
}