package ru.taxcom.feedback.ui.detail

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import ru.taxcom.composeUI.CircularProgressBar
import ru.taxcom.composeUI.EmailInputField
import ru.taxcom.composeUI.Header
import ru.taxcom.composeUI.SampleMessage
import ru.taxcom.composeUI.SampleToolbar
import ru.taxcom.feedback.R
import ru.taxcom.feedback.components.AttachmentBottomSheet
import ru.taxcom.feedback.components.CloudMessage
import ru.taxcom.feedback.components.MessageField
import ru.taxcom.feedback.data.ui.ChatUI
import ru.taxcom.feedback.data.ui.ImageUI
import ru.taxcom.feedback.data.ui.MessagePosition
import ru.taxcom.feedback.databinding.FeedbackDetailLayoutBinding
import ru.taxcom.feedback.ui.base.BaseFeedbackScreenFragment
import com.example.feedback_jetpack_compose.utils.MAX_COUNT_OF_SELECTED_ATTACHMENTS
import com.example.feedback_jetpack_compose.utils.mapToChatUI
import ru.taxcom.taxcomkit.data.resource.ResourceStatus
import ru.taxcom.taxcomkit.ui.snackbar.Snackbar
import ru.taxcom.taxcomkit.ui.view.activitybars.colorNavigationBarOn27ApiAndHigher
import ru.taxcom.taxcomkit.ui.view.activitybars.colorStatusBarOn23ApiAndHigher
import ru.taxcom.taxcomkit.utils.getArgument
import ru.taxcom.taxcomkit.utils.hideKeyboard
import ru.taxcom.taxcomkit.utils.permissions.hasPermissionsToAttachments
import ru.taxcom.taxcomkit.utils.putToArgument

class DetailFeedbackFragment : BaseFeedbackScreenFragment<FeedbackDetailLayoutBinding>() {

    override val navigationBarColor: Int
        get() = R.color.screen_background_gray_color

    override val statusBarColor: Int
        get() = R.color.screen_background_gray_color

    private val viewModel: DetailFeedbackViewModel by viewModels { viewModelFactory }

    private var messageList = emptyList<ChatUI>()

    private val onBackButtonClick: () -> Unit = {
        navToBack()
    }

    private val onMessageChange: (String) -> Unit = {
        viewModel.updateMessage(it)
    }

    private val onEmailChange: (String) -> Unit = {
        viewModel.updateEmail(it)
    }

    private val onSendClick: () -> Unit = {
        if (viewModel.chatId.value.isEmpty()) viewModel.sendFeedback()
        else viewModel.sendMessage()

        viewModel.clearAttachments()
        viewModel.clearMessage()
        hideKeyboard()
    }

    private val onRemoveAttachment: (Uri) -> Unit = {
        viewModel.removeAttachment(it)
    }

    private val timerToUpdateChat = object : CountDownTimer(TIME_TO_UPDATE_CHAT, TIME_STEP) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            if (isAdded.not()) return
            if (viewModel.chatId.value.isEmpty()) return
            if (viewModel.messages.value?.data?.closed == false) restartTimer()
            viewModel.getChatInformation()
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FeedbackDetailLayoutBinding = FeedbackDetailLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavbar()
        val chatId = getArgument<String>(FEED_BACK_CHAT_ID_KEY, removeArgument = false)
        viewModel.setChatId(chatId ?: "")
        binding.composeDetailFeedbackView.setContent { DetailFeedbackScreen() }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun DetailFeedbackScreen() {
        val chatArgumentId = getArgument<String>(FEED_BACK_CHAT_ID_KEY, removeArgument = false)
        val isVisibleBottomSheet = remember { mutableStateOf(false) }
        val isPossibleAttachment = remember { mutableStateOf(true) }
        val keyboardController = LocalSoftwareKeyboardController.current

        val messages by viewModel.messages.collectAsState()
        val createChatResponse by viewModel.createChatResult
        val email by viewModel.userEmail.collectAsState()
        val chatAttachment by viewModel.userAttachment
        val chatId by viewModel.chatId

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    indication = null,
                    onClick = { keyboardController?.hide() },
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            Column(Modifier.fillMaxSize()) {
                val title = chatArgumentId?.let {
                    getString(R.string.applying_number_text, chatArgumentId)
                } ?: run {
                    getString(R.string.create_applying_text)
                }
                SampleToolbar(title, onBackButtonClick)

                when {
                    chatId.isEmpty() -> {
                        EmailInputField(onEmailChange)
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    else -> viewModel.getChatInformation()
                }

                when (createChatResponse?.status) {
                    ResourceStatus.LOADING -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressBar(modifier = Modifier)
                    }

                    ResourceStatus.SUCCESS -> {
                        showMessage(
                            title = getString(R.string.application_submitted_title),
                            subtitle = getString(R.string.application_submitted_text),
                            colorId = R.color.PrimaryForegroundColor
                        )
                        viewModel.setChatId(createChatResponse?.data?.id?.toString() ?: "")
                        viewModel.clearFeedbackResponse()
                    }

                    ResourceStatus.ERROR -> {
                        showMessage(
                            title = getString(R.string.error_title),
                            subtitle = createChatResponse?.message
                                ?: createChatResponse?.messageTitle
                                ?: "",
                            colorId = R.color.AccentColor
                        )
                        viewModel.clearFeedbackResponse()
                    }

                    else -> {}
                }

                when (messages?.status) {
                    ResourceStatus.LOADING -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressBar(modifier = Modifier)
                        }
                    }

                    null -> {}

                    else -> {
                        timerToUpdateChat.start()
                        messageList = emptyList()
                        val login = messages?.data?.login ?: ""
                        messageList =
                            messages?.data?.messages?.map { it.copy() }?.mapToChatUI(login)
                                ?: emptyList()
                        isPossibleAttachment.value = messageList.filterIsInstance<ImageUI>().count {
                            it.position == MessagePosition.RIGHT
                        } < MAX_COUNT_OF_SELECTED_ATTACHMENTS
                        val groupedMessages = messageList.groupBy {
                            it.date
                        }
                        val lazyListState = rememberLazyListState()
                        LaunchedEffect(messageList.size) {
                            lazyListState.animateScrollToItem(messageList.size)
                        }
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    bottom = dimensionResource(id = R.dimen.xl_4_dimen)
                                )
                        ) {
                            groupedMessages.forEach { (date, items) ->
                                item {
                                    Header(
                                        date,
                                        Alignment.Center,
                                        bottomMarginId = R.dimen.ml_dimen,
                                        startMarginId = R.dimen.zero_dimen,
                                        topMarginId = R.dimen.zero_dimen,
                                        endMarginId = R.dimen.zero_dimen
                                    )
                                }
                                items(items) { item ->
                                    CloudMessage(item)
                                }
                            }
                        }
                        if (messages?.status == ResourceStatus.ERROR) {
                            SampleMessage(getString(R.string.feedback_detail_error))
                            showMessage(
                                title = getString(R.string.error_title),
                                subtitle = messages?.message ?: messages?.messageTitle ?: "",
                                colorId = R.color.AccentColor
                            )
                        }
                    }
                }
            }
            if (messages?.data?.closed == false || chatId.isEmpty()) MessageField(
                onMessageChange, chatAttachment, onSendClick,
                onRemoveAttachment = onRemoveAttachment,
                onAttachClick = {
                    hideKeyboard()
                    if (isPossibleAttachment.value)
                        isVisibleBottomSheet.value = hasPermissionsToAttachments(
                            requireActivity(), REQUEST_MEDIA_CODE, REQUEST_STORAGE_CODE
                        )
                    else
                        showMessage(
                            subtitle = getString(R.string.attahcment_not_possible_message),
                            colorId = R.color.SecondaryForegroundColor,
                            gravity = Gravity.TOP
                        )
                },
                modifier = Modifier.align(Alignment.BottomCenter),
                email = email,
                possibilityToAttach = isPossibleAttachment.value
            )
            if (isVisibleBottomSheet.value) {
                hideKeyboard()
                val images = getImagesFromGallery(requireActivity())
                AttachmentBottomSheet(
                    Modifier.align(Alignment.BottomCenter),
                    images = images,
                    selectedImages = chatAttachment,
                    {
                        isVisibleBottomSheet.value = false
                    },
                    MAX_COUNT_OF_SELECTED_ATTACHMENTS,
                    {
                        viewModel.attachFiles(it)
                        isVisibleBottomSheet.value = false
                    })
            }
        }
    }

    private fun initNavbar() = with(requireActivity()) {
        colorNavigationBarOn27ApiAndHigher(navigationBarColor)
        colorStatusBarOn23ApiAndHigher(statusBarColor)
    }

    private fun showMessage(
        title: String? = null, subtitle: String, colorId: Int, gravity: Int = Gravity.BOTTOM
    ) = Snackbar.showPositiveSnackBar(
        rootView = binding.root,
        titleText = title,
        gravity = gravity,
        subtitleText = subtitle,
        activity = requireActivity(),
        backgroundColor = ContextCompat.getColor(requireContext(), colorId)
    )

    private fun restartTimer() {
        timerToUpdateChat.cancel()
        timerToUpdateChat.start()
    }

    companion object {
        private const val FEED_BACK_CHAT_ID_KEY = "feed_back_chat_id_key"
        private const val TIME_TO_UPDATE_CHAT = 15 * DateUtils.SECOND_IN_MILLIS
        private const val TIME_STEP = 1 * DateUtils.SECOND_IN_MILLIS

        fun newInstance(id: String? = null) =
            DetailFeedbackFragment().putToArgument(FEED_BACK_CHAT_ID_KEY, id)
    }
}