package ru.taxcom.feedback.data.ui

class FeedbackCardItem(
    val title: Int,
    val image: Int,
    val callback: () -> Unit,
    val notifications: Int? = null
)