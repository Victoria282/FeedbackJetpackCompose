package ru.taxcom.feedback.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import ru.taxcom.composeUI.CircularProgressBar
import ru.taxcom.composeUI.Header
import ru.taxcom.composeUI.SampleMessage
import ru.taxcom.composeUI.SampleToolbar
import ru.taxcom.feedback.R
import ru.taxcom.feedback.components.FeedbackHistoryItem
import ru.taxcom.feedback.data.FeedbackHistoryItem
import ru.taxcom.feedback.databinding.HistoryFeedbackLayoutBinding
import ru.taxcom.feedback.ui.base.BaseFeedbackScreenFragment
import ru.taxcom.taxcomkit.data.resource.ResourceStatus
import ru.taxcom.taxcomkit.ui.snackbar.Snackbar
import ru.taxcom.taxcomkit.utils.formatter.Formatter.DATE_API_SENT
import ru.taxcom.taxcomkit.utils.formatter.convertServerDateToApp
import java.text.SimpleDateFormat
import java.util.Locale

class FeedbackHistoryFragment : BaseFeedbackScreenFragment<HistoryFeedbackLayoutBinding>() {

    override val navigationBarColor: Int
        get() = R.color.screen_background_gray_color

    override val statusBarColor: Int
        get() = R.color.screen_background_gray_color

    private val viewModel: FeedbackHistoryViewModel by viewModels { viewModelFactory }

    private val formatter = SimpleDateFormat(DATE_API_SENT, Locale.getDefault())

    private var historyList = emptyList<FeedbackHistoryItem>()

    private val onBackButtonClick: () -> Unit = {
        navToBack()
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): HistoryFeedbackLayoutBinding =
        HistoryFeedbackLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeHistoryFeedbackView.setContent {
            val feedbackState by viewModel.feedbackHistory.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
            ) {
                Column {
                    SampleToolbar(getString(R.string.history_of_applying_text), onBackButtonClick)

                    when (feedbackState.status) {
                        ResourceStatus.LOADING -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressBar(modifier = Modifier)
                        }

                        ResourceStatus.SUCCESS -> {
                            historyList = emptyList()
                            historyList = feedbackState.data?.map { it.copy() } ?: emptyList()
                            val sortedHistoryList = historyList.sortedByDescending {
                                it.created?.let(formatter::parse)
                            }
                            sortedHistoryList.onEach { it.created = convertServerDateToApp(it.created) }
                            val groupedFeedback = sortedHistoryList.groupBy { it.created }
                            if (historyList.isEmpty()) SampleMessage(getString(R.string.feedback_history_list_is_empty))
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                groupedFeedback.forEach { (date, items) ->
                                    item {
                                        Header(
                                            title = date ?: "",
                                            alignment = Alignment.TopStart,
                                            bottomMarginId = R.dimen.ml_dimen,
                                            endMarginId = R.dimen.zero_dimen,
                                            startMarginId = R.dimen.ml_dimen,
                                            topMarginId = R.dimen.xl_dimen
                                        )
                                    }
                                    items(items) { item ->
                                        FeedbackHistoryItem(item = item, callback = {
                                            innerNavigator?.navigateToDetails(
                                                item.id?.toString() ?: return@FeedbackHistoryItem
                                            )
                                        })
                                    }
                                }
                            }
                        }

                        ResourceStatus.ERROR -> {
                            SampleMessage(getString(R.string.feedback_history_list_is_error_empty))
                            Snackbar.showPositiveSnackBar(
                                rootView = binding.root,
                                titleText = getString(R.string.error_title),
                                subtitleText = feedbackState.message ?: feedbackState.messageTitle,
                                activity = requireActivity(),
                                backgroundColor = ContextCompat.getColor(
                                    requireContext(), R.color.AccentColor
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = FeedbackHistoryFragment()
    }
}