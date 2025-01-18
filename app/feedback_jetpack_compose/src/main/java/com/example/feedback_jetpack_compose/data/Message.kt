package ru.taxcom.feedback.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerializedName("message") var message: String? = null,
    @SerializedName("image") var image: String? = null
)