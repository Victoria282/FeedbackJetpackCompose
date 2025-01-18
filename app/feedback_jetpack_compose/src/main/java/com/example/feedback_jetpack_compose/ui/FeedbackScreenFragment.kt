package ru.taxcom.feedback.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import ru.taxcom.composeUI.FullWidthBanner
import ru.taxcom.feedback.R
import ru.taxcom.feedback.components.CreateFeedbackCards
import ru.taxcom.composeUI.SampleToolbar
import ru.taxcom.feedback.data.ui.FeedbackCardItem
import ru.taxcom.feedback.databinding.FeedbackScreenLayoutBinding
import ru.taxcom.feedback.ui.base.BaseFeedbackScreenFragment
import ru.taxcom.taxcomkit.ui.view.activitybars.colorNavigationBarOn27ApiAndHigher
import ru.taxcom.taxcomkit.ui.view.activitybars.colorStatusBarOn23ApiAndHigher

class FeedbackScreenFragment : BaseFeedbackScreenFragment<FeedbackScreenLayoutBinding>() {

    override val navigationBarColor: Int
        get() = R.color.screen_background_gray_color

    override val statusBarColor: Int
        get() = R.color.screen_background_gray_color

    private val viewModel: FeedbackScreenViewModel by viewModels { viewModelFactory }

    private val onBackButtonClick: () -> Unit = {
        navToBack()
    }

    private val historyChatNavigate: () -> Unit = {
        innerNavigator?.navigateToChatHistory()
    }

    private val createChatNavigate: () -> Unit = {
        innerNavigator?.navigateToDetails()
    }

    private val cards by lazy {
        listOf(
            FeedbackCardItem(
                R.string.create_applying_text, R.drawable.create_applying_icon, createChatNavigate
            ),
            FeedbackCardItem(
                R.string.history_of_applying_text,
                R.drawable.history_applying_icon,
                historyChatNavigate
            )
        )
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FeedbackScreenLayoutBinding = FeedbackScreenLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeFeedbackView.setContent {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                SampleToolbar(getString(R.string.feedback_title), onBackButtonClick)
                FullWidthBanner(
                    iconId = R.drawable.feedback_banner_icon,
                    getString(R.string.feedback_banner_text),
                    backgroundColor = R.color.PrimaryForegroundColor,
                    R.dimen.feedback_banner_size
                )
                Spacer(modifier = Modifier.width(20.dp))
                CreateFeedbackCards(cards)
            }
        }
        with(requireActivity()) {
            colorNavigationBarOn27ApiAndHigher(navigationBarColor)
            colorStatusBarOn23ApiAndHigher(statusBarColor)
        }
    }

    companion object {
        fun newInstance() = FeedbackScreenFragment()
    }
}