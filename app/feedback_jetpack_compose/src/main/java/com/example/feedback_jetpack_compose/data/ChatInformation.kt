package ru.taxcom.feedback.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ChatInformation(
    @SerializedName("messages") var messages: ArrayList<Messages> = arrayListOf(),
    @SerializedName("serviceUrl") var serviceUrl: String? = null,
    @SerializedName("created") var created: String? = null,
    @SerializedName("closed") var closed: Boolean? = null,
    @SerializedName("readed") var readed: Boolean? = null,
    @SerializedName("topic") var topic: String? = null,
    @SerializedName("login") var login: String? = null,
    @SerializedName("app") var app: String? = null,
    @SerializedName("id") var id: Int? = null
)