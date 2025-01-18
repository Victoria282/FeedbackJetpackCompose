package ru.taxcom.feedback.usecase.impl

import ru.taxcom.feedback.data.FeedbackHistoryItem
import ru.taxcom.feedback.repository.FeedbackRepository
import ru.taxcom.feedback.usecase.GetFeedbackHistoryUseCase
import ru.taxcom.taxcomkit.data.resource.Resource
import javax.inject.Inject

class GetFeedbackHistoryUseCaseImpl @Inject constructor(
    private val repository: FeedbackRepository,
) : GetFeedbackHistoryUseCase {
    override suspend fun getHistory(): Resource<List<FeedbackHistoryItem>> = repository.getHistory()
}