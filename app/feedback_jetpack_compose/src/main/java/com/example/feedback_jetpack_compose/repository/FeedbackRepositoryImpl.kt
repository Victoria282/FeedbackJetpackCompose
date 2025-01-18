package ru.taxcom.feedback.repository

import ru.taxcom.feedback.api.FeedbackApi
import ru.taxcom.feedback.data.ChatInformation
import ru.taxcom.feedback.data.CreateChatResponse
import ru.taxcom.feedback.data.FeedbackHistoryItem
import ru.taxcom.feedback.data.Message
import ru.taxcom.feedback.data.CreateChatModel
import ru.taxcom.feedback.interactor.FeedbackModuleInteractor
import ru.taxcom.taxcomkit.data.resource.Resource
import ru.taxcom.taxcomkit.utils.internet.processHttpResponse
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val feedback: FeedbackModuleInteractor,
    private val ap: FeedbackApi
) : FeedbackRepository {

    override suspend fun createChat(model: CreateChatModel): Resource<CreateChatResponse> =
        processHttpResponse<CreateChatResponse, CreateChatResponse>(
            response = {
                ap.createChat(
                    feedback.updateChatModel(model),
                    feedback.createFeedbackHeaders() ?: return Resource.error(data = null)
                )
            })

    override suspend fun getHistory(): Resource<List<FeedbackHistoryItem>> =
        processHttpResponse<List<FeedbackHistoryItem>, List<FeedbackHistoryItem>>(
            response = {
                ap.getHistory(
                    feedback.createFeedbackHeaders() ?: return Resource.error(data = null)
                )
            })

    override suspend fun getChat(id: String): Resource<ChatInformation> =
        processHttpResponse<ChatInformation, ChatInformation>(
            response = {
                ap.getChat(
                    id, feedback.createFeedbackHeaders() ?: return Resource.error(data = null)
                )
            }
        )

    override suspend fun sendMessage(id: String, message: Message): Resource<ChatInformation> =
        processHttpResponse<ChatInformation, ChatInformation>(
            response = {
                ap.sendMessage(
                    id, message,
                    feedback.createFeedbackHeaders() ?: return Resource.error(data = null)
                )
            }
        )
}