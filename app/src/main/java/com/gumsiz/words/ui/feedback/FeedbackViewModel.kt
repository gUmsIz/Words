package com.gumsiz.words.ui.feedback

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumsiz.shared.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class FeedbackStatus {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}

class FeedbackViewModel(private val repository: Repository) : ViewModel() {

    val email = MutableStateFlow("")
    val message = MutableStateFlow("")

    val emailError = MutableStateFlow<String?>(null)
    val messageError = MutableStateFlow<String?>(null)

    private val _status = MutableStateFlow(FeedbackStatus.IDLE)
    val status = _status.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
        if (emailError.value != null) {
            emailError.value = null
        }
    }

    fun onMessageChanged(newMessage: String) {
        message.value = newMessage
        if (messageError.value != null) {
            messageError.value = null
        }
    }

    fun submitFeedback(appVersion: String = "", buildNumber: String = "") {
        val trimmedEmail = email.value.trim()
        val trimmedMessage = message.value.trim()

        var hasError = false
        if (trimmedEmail.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            emailError.value = "INVALID_EMAIL"
            hasError = true
        }

        if (trimmedMessage.isEmpty()) {
            messageError.value = "EMPTY_MESSAGE"
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {
            _status.value = FeedbackStatus.LOADING
            val success = repository.sendFeedback(
                email = trimmedEmail,
                message = trimmedMessage,
                platform = "Android",
                appVersion = appVersion,
                buildNumber = buildNumber,
                bundleId = "com.gumsiz.words"
            )
            if (success) {
                _status.value = FeedbackStatus.SUCCESS
            } else {
                _status.value = FeedbackStatus.ERROR
            }
        }
    }

    fun resetStatus() {
        _status.value = FeedbackStatus.IDLE
    }
}
