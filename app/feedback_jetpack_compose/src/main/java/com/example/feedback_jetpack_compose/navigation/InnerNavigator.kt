package ru.taxcom.feedback.navigation

import ru.taxcom.taxcomkit.ui.basefragment.CommonNavigator

interface InnerNavigator : CommonNavigator {
    fun navigateToDetails(id: String? = null)
    fun navigateToChatHistory()
}