package ru.taxcom.feedback.usecase

import ru.taxcom.feedback.data.ChatInformation
import ru.taxcom.feedback.data.Message
import ru.taxcom.taxcomkit.data.resource.Resource

interface SendMessageUseCase {
    suspend fun sendMessage(id: String, message: Message): Resource<ChatInformation>
}