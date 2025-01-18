package ru.taxcom.feedback.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Messages(
    @SerializedName("message") var message: String? = null,
    @SerializedName("login") var login: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("id") var id: String? = null
)