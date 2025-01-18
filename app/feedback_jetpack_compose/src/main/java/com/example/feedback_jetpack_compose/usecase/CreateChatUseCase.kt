package ru.taxcom.feedback.usecase

import ru.taxcom.feedback.data.CreateChatModel
import ru.taxcom.feedback.data.CreateChatResponse
import ru.taxcom.taxcomkit.data.resource.Resource

interface CreateChatUseCase {
    suspend fun createChat(model: CreateChatModel): Resource<CreateChatResponse>
}