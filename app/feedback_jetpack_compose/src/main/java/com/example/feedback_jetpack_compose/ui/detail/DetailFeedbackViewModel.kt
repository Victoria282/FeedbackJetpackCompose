package ru.taxcom.feedback.ui.detail

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.taxcom.feedback.R
import ru.taxcom.feedback.data.ChatInformation
import ru.taxcom.feedback.data.CreateChatModel
import ru.taxcom.feedback.data.CreateChatResponse
import ru.taxcom.feedback.data.Message
import ru.taxcom.feedback.usecase.CreateChatUseCase
import ru.taxcom.feedback.usecase.GetChatUseCase
import ru.taxcom.feedback.usecase.SendMessageUseCase
import com.example.feedback_jetpack_compose.utils.convertToBase64
import ru.taxcom.taxcomkit.data.resource.Resource
import ru.taxcom.taxcomkit.docupload.UploadDocumentInteractor
import ru.taxcom.taxcomkit.utils.resource.ResourceProvider
import java.io.File
import javax.inject.Inject

class DetailFeedbackViewModel @Inject constructor(
    private val documentInteractor: UploadDocumentInteractor,
    private val sendMessageUseCase: SendMessageUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val resourceProvider: ResourceProvider,
    private val getChatUseCase: GetChatUseCase
) : ViewModel() {

    private val _createChatResult = mutableStateOf<Resource<CreateChatResponse>?>(null)
    val createChatResult: State<Resource<CreateChatResponse>?> get() = _createChatResult

    private val _messages = MutableStateFlow<Resource<ChatInformation>?>(null)
    val messages: StateFlow<Resource<ChatInformation>?> get() = _messages

    private val _userAttachment = mutableStateOf<List<Uri>>(emptyList())
    val userAttachment: State<List<Uri>> get() = _userAttachment

    private val _userMessage = mutableStateOf("")
    val userMessage: State<String> get() = _userMessage

    private val _chatId = mutableStateOf("")
    val chatId: State<String> get() = _chatId

    private val _userEmail = MutableStateFlow<String?>("")
    val userEmail: StateFlow<String?> get() = _userEmail

    fun setChatId(chatId: String) {
        _chatId.value = chatId
        if (chatId.isEmpty()) return
        _userEmail.value = null
        _messages.value = Resource.loading()
        getChatInformation()
    }

    fun attachFiles(images: List<Uri>) {
        _userAttachment.value = images
    }

    fun updateMessage(message: String) {
        _userMessage.value = message
    }

    fun updateEmail(email: String) {
        _userEmail.value = email
    }

    fun clearMessage() {
        _userMessage.value = ""
    }

    fun clearAttachments() {
        _userAttachment.value = emptyList()
    }

    fun removeAttachment(uri: Uri) {
        val currentAttachments = _userAttachment.value.toMutableList()
        currentAttachments.remove(uri)
        _userAttachment.value = currentAttachments
    }

    fun sendMessage() = viewModelScope.launch {
        val attachment = userAttachment.value.firstOrNull()
        val path = attachment?.let {
            documentInteractor.getActualPath(it)
        }
        val base64 = path?.let {
            convertToBase64(File(it))
        }
        try {
            val feedback = Message(
                image = base64, message = userMessage.value
            )
            val result = sendMessageUseCase.sendMessage(chatId.value, feedback)

            _messages.value = result
        } catch (e: Exception) {
            val chatData = _messages.value?.data
            _messages.value = Resource.error(
                messageTitle = resourceProvider.getString(R.string.error_title),
                message = resourceProvider.getString(R.string.feedback_error_connection),
                data = chatData
            )
        }
    }

    fun getChatInformation() = viewModelScope.launch {
        try {
            val result = getChatUseCase.getChat(chatId.value)
            _messages.value = result
        } catch (e: Exception) {
            val chatData = _messages.value?.data
            _messages.value = Resource.error(
                messageTitle = resourceProvider.getString(R.string.error_title),
                message = resourceProvider.getString(R.string.feedback_error_connection),
                data = chatData
            )
        }
    }

    fun sendFeedback() = viewModelScope.launch {
        _createChatResult.value = Resource.loading()
        try {
            val attachment = userAttachment.value.firstOrNull()
            val path = attachment?.let {
                documentInteractor.getActualPath(it)
            }
            val base64 = path?.let {
                convertToBase64(File(it))
            }
            val requestModel = CreateChatModel(
                image = base64, email = userEmail.value, message = userMessage.value
            )
            val result = createChatUseCase.createChat(requestModel)
            _createChatResult.value = result
        } catch (e: Exception) {
            _createChatResult.value = Resource.error(
                messageTitle = resourceProvider.getString(R.string.error_title),
                message = resourceProvider.getString(R.string.feedback_error_connection)
            )
        }
    }

    fun clearFeedbackResponse() {
        _createChatResult.value = null
    }
}