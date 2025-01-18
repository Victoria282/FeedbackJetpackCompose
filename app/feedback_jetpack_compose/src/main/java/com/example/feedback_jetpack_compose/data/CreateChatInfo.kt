package ru.taxcom.feedback.data

import com.google.gson.annotations.SerializedName

data class CreateChatInfo(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("app") var app: String? = null,
    @SerializedName("login") var login: String? = null,
    @SerializedName("topic") var topic: String? = null,
    @SerializedName("closed") var closed: Boolean? = null,
    @SerializedName("readed") var readed: Boolean? = null,
    @SerializedName("created") var created: String? = null,
    @SerializedName("serviceUrl") var serviceUrl: String? = null
)