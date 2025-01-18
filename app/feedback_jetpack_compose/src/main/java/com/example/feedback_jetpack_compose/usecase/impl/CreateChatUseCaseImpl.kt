package ru.taxcom.feedback.usecase.impl

import ru.taxcom.feedback.data.CreateChatModel
import ru.taxcom.feedback.data.CreateChatResponse
import ru.taxcom.feedback.repository.FeedbackRepository
import ru.taxcom.feedback.usecase.CreateChatUseCase
import ru.taxcom.taxcomkit.data.resource.Resource
import javax.inject.Inject

class CreateChatUseCaseImpl @Inject constructor(
    private val repository: FeedbackRepository
) : CreateChatUseCase {
    override suspend fun createChat(model: CreateChatModel): Resource<CreateChatResponse> =
        repository.createChat(model)
}