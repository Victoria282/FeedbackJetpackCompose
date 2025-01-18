package ru.taxcom.feedback.repository

import ru.taxcom.feedback.data.ChatInformation
import ru.taxcom.feedback.data.CreateChatModel
import ru.taxcom.feedback.data.CreateChatResponse
import ru.taxcom.feedback.data.FeedbackHistoryItem
import ru.taxcom.feedback.data.Message
import ru.taxcom.taxcomkit.data.resource.Resource

interface FeedbackRepository {
    suspend fun sendMessage(id: String, message: Message): Resource<ChatInformation>
    suspend fun createChat(model: CreateChatModel): Resource<CreateChatResponse>
    suspend fun getHistory(): Resource<List<FeedbackHistoryItem>>
    suspend fun getChat(id: String): Resource<ChatInformation>
}