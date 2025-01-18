package ru.taxcom.feedback.usecase.impl

import ru.taxcom.feedback.data.ChatInformation
import ru.taxcom.feedback.repository.FeedbackRepository
import ru.taxcom.feedback.usecase.GetChatUseCase
import ru.taxcom.taxcomkit.data.resource.Resource
import javax.inject.Inject

class GetChatUseCaseImpl @Inject constructor(
    private val repository: FeedbackRepository
) : GetChatUseCase {
    override suspend fun getChat(id: String): Resource<ChatInformation> = repository.getChat(id)
}