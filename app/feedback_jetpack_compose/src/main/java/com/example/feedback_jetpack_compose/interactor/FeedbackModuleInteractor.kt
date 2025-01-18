package ru.taxcom.feedback.interactor

import ru.taxcom.feedback.data.CreateChatModel
import ru.taxcom.feedback.data.FeedbackHeaders

interface FeedbackModuleInteractor {
    fun updateChatModel(model: CreateChatModel): CreateChatModel
    fun createFeedbackHeaders(): FeedbackHeaders?
}