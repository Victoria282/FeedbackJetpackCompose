package ru.taxcom.feedback.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateChatModel(
    @SerializedName("inn") var inn: String = "",
    @SerializedName("topic") var topic: String = "",
    @SerializedName("image") var image: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("abonentId") var abonentId: String = "",
    @SerializedName("cabinetId") var cabinetId: String? = null,
    @SerializedName("employeeId") var employeeId: String? = null,
    @SerializedName("externalId") var externalId: String? = null,
    @SerializedName("edxClientId") var edxClientId: String? = null,
    @SerializedName("organizationName") var organizationName: String? = null
)