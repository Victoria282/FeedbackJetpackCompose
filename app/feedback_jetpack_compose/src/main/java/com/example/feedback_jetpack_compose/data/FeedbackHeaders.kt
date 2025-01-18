package ru.taxcom.feedback.data

data class FeedbackHeaders(
    val serviceUrl: String,
    val deviceId: String,
    val abonentId: String,
    val userName: String,
    val userInn: String,
    val userKpp: String,
    val orgName: String,
    val login: String
)