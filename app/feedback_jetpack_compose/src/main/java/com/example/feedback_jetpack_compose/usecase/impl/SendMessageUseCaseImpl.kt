package ru.taxcom.feedback.usecase.impl

import ru.taxcom.feedback.data.ChatInformation
import ru.taxcom.feedback.data.Message
import ru.taxcom.feedback.repository.FeedbackRepository
import ru.taxcom.feedback.usecase.SendMessageUseCase
import ru.taxcom.taxcomkit.data.resource.Resource
import javax.inject.Inject

class SendMessageUseCaseImpl @Inject constructor(
    private val repository: FeedbackRepository
): SendMessageUseCase {
    override suspend fun sendMessage(id: String, message: Message): Resource<ChatInformation> =
        repository.sendMessage(id, message)
}