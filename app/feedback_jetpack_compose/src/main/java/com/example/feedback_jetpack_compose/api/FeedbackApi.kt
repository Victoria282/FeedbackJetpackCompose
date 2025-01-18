package ru.taxcom.feedback.api

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import ru.taxcom.feedback.data.CreateChatModel
import ru.taxcom.feedback.data.FeedbackHeaders
import ru.taxcom.feedback.data.Message
import ru.taxcom.taxcomkit.network.Ktor
import ru.taxcom.taxcomkit.utils.Utils.getApiKey
import javax.inject.Inject

interface FeedbackApi {
    suspend fun sendMessage(id: String, message: Message, headers: FeedbackHeaders): HttpResponse
    suspend fun createChat(model: CreateChatModel, headers: FeedbackHeaders): HttpResponse
    suspend fun getChat(id: String, headers: FeedbackHeaders): HttpResponse
    suspend fun getHistory(headers: FeedbackHeaders): HttpResponse
}

class FeedbackApiImpl @Inject constructor(
    private val context: Context,
    private val ktor: Ktor
) : FeedbackApi {
    private val api = "https://advert-mobile.taxcom.ru"

    private val client: HttpClient by lazy {
        val client: HttpClient = ktor.ktorHttpClient
        client.plugin(HttpSend).intercept { request ->
            execute(request)
        }
        client
    }

    override suspend fun getHistory(headers: FeedbackHeaders): HttpResponse = client.get {
        url("$api/api/v1/support/chats")
        contentType(ContentType.Application.Json)
        chatAuth(headers)
    }

    override suspend fun getChat(id: String, headers: FeedbackHeaders): HttpResponse = client.get {
        url("$api/api/v1/support/chats/${id}")
        contentType(ContentType.Application.Json)
        chatAuth(headers)
    }

    override suspend fun sendMessage(
        id: String, message: Message, headers: FeedbackHeaders
    ): HttpResponse = client.post {
        url("$api/api/v1/support/chats/${id}/message")
        contentType(ContentType.Application.Json)
        setBody(ktor.json.encodeToString(message))
        chatAuth(headers)
    }

    override suspend fun createChat(
        model: CreateChatModel, headers: FeedbackHeaders
    ): HttpResponse = client.post {
        url("$api/api/v1/support/chats")
        setBody(ktor.json.encodeToString(model))
        contentType(ContentType.Application.Json)
        chatAuth(headers)
    }

    private fun HttpRequestBuilder.chatAuth(headers: FeedbackHeaders) {
        header("Authorization", "Bearer ${context.getApiKey()}")
        header("service_url", headers.serviceUrl)
        header("abonent_id", headers.abonentId)
        header("user_name", headers.userName)
        header("device_id", headers.deviceId)
        header("user_inn", headers.userInn)
        header("user_kpp", headers.userKpp)
        header("org_name", headers.orgName)
        header("login", headers.login)
    }
}