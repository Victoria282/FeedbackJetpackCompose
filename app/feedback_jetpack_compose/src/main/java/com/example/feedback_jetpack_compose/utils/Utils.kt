package com.example.feedback_jetpack_compose.utils

import android.util.Base64
import ru.taxcom.feedback.data.Messages
import ru.taxcom.feedback.data.ui.ChatUI
import ru.taxcom.feedback.data.ui.ImageUI
import ru.taxcom.feedback.data.ui.MessagePosition
import ru.taxcom.feedback.data.ui.MessageUI
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import java.io.File

fun List<Messages>.mapToChatUI(login: String): List<ChatUI> = this.flatMap {
    val date = convertServerDateToApp(it.date)
    val time = convertServerTimeToApp(it.date)
    val position = if (it.login == login) MessagePosition.RIGHT else MessagePosition.LEFT
    val chatUIList = mutableListOf<ChatUI>()
    if (!it.image.isNullOrEmpty()) {
        chatUIList.add(ImageUI(image = it.image ?: "", date = date, time = time, position = position))
    }
    if (!it.message.isNullOrEmpty()) {
        chatUIList.add(MessageUI(message = it.message ?: "", date = date, time = time, position = position))
    }
    chatUIList
}
const val DATE_API_SENT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val TIME_SIMPLE_FORMAT: String = "HH:mm"
const val WEEK_AND_DATE = "E, dd.MM.yyyy"
const val NO_DATA_STRING = "-"

fun parseDocumentDate(serverDate: String?): DateTime? {
    if (serverDate == null) return null
    val serverDateFormatter = DateTimeFormat.forPattern(DATE_API_SENT)
    return serverDateFormatter.parseDateTime(serverDate)
}

fun convertServerDateToApp(serverDate: String?): String {
    val date = parseDocumentDate(serverDate) ?: return NO_DATA_STRING
    val formatter = DateTimeFormat.forPattern(WEEK_AND_DATE)
    return date.toString(formatter).replaceFirstChar { it.uppercase() }
}

fun convertServerTimeToApp(serverDate: String?): String {
    val date = parseDocumentDate(serverDate) ?: return NO_DATA_STRING
    val formatter = DateTimeFormat.forPattern(TIME_SIMPLE_FORMAT)
    return date.toString(formatter)
}

fun convertToBase64(attachment: File): String =
    Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)

const val MAX_COUNT_OF_SELECTED_ATTACHMENTS = 1