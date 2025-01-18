package ru.taxcom.feedback.data.ui

data class MessageUI(
    val message: String,
    override var date: String,
    override var time: String,
    override val position: MessagePosition
) : ChatUI

data class ImageUI(
    var image: String,
    override var date: String,
    override var time: String,
    override val position: MessagePosition
) : ChatUI

interface ChatUI {
    var date: String
    var time: String
    val position: MessagePosition
}

enum class MessagePosition {
    LEFT, RIGHT
}