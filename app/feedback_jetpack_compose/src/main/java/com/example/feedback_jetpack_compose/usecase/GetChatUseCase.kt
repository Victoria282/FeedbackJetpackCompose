package ru.taxcom.feedback.usecase

import ru.taxcom.feedback.data.ChatInformation
import ru.taxcom.taxcomkit.data.resource.Resource

interface GetChatUseCase {
    suspend fun getChat(id: String): Resource<ChatInformation>
}