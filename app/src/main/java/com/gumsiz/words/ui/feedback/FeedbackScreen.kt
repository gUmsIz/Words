package com.gumsiz.words.ui.feedback

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gumsiz.words.R
import com.gumsiz.words.ui.theme.primaryDarkColor
import com.gumsiz.words.ui.theme.primaryLightColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedbackScreen(
    navController: NavController,
    viewModel: FeedbackViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsState()
    val message by viewModel.message.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val messageError by viewModel.messageError.collectAsState()
    val status by viewModel.status.collectAsState()

    val packageInfo: PackageInfo? = remember {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
        } catch (e: Exception) {
            null
        }
    }

    val appVersion = packageInfo?.versionName ?: ""
    val buildNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo?.longVersionCode?.toString() ?: ""
    } else {
        @Suppress("DEPRECATION")
        packageInfo?.versionCode?.toString() ?: ""
    }

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(WindowInsets.statusBars.asPaddingValues()),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.feedback_title), color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.Black,
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.Black
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (status == FeedbackStatus.SUCCESS) {
                    Card(
                        shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
                        backgroundColor = primaryLightColor,
                        border = BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(id = R.string.feedback_success),
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    viewModel.resetStatus()
                                    navController.popBackStack()
                                },
                                shape = MaterialTheme.shapes.medium.copy(all = CornerSize(12.dp)),
                                colors = ButtonDefaults.buttonColors(backgroundColor = primaryDarkColor)
                            ) {
                                Text(text = "OK", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    Card(
                        shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
                        backgroundColor = primaryLightColor,
                        border = BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.feedback_subtitle),
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            OutlinedTextField(
                                value = email,
                                onValueChange = { viewModel.onEmailChanged(it) },
                                label = { Text(text = stringResource(id = R.string.feedback_email_hint)) },
                                isError = emailError != null,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.White,
                                    focusedBorderColor = primaryDarkColor,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color(0xFF333333),
                                    unfocusedLabelColor = Color(0xFF616161),
                                    placeholderColor = Color(0xFF757575),
                                    cursorColor = Color.Black
                                )
                            )
                            if (emailError != null) {
                                Text(
                                    text = stringResource(id = R.string.feedback_invalid_email),
                                    color = MaterialTheme.colors.error,
                                    style = MaterialTheme.typography.caption,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = message,
                                onValueChange = { viewModel.onMessageChanged(it) },
                                label = { Text(text = stringResource(id = R.string.feedback_message_hint)) },
                                isError = messageError != null,
                                minLines = 5,
                                maxLines = 8,
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.White,
                                    focusedBorderColor = primaryDarkColor,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color(0xFF333333),
                                    unfocusedLabelColor = Color(0xFF616161),
                                    placeholderColor = Color(0xFF757575),
                                    cursorColor = Color.Black
                                )
                            )
                            if (messageError != null) {
                                Text(
                                    text = stringResource(id = R.string.feedback_empty_message),
                                    color = MaterialTheme.colors.error,
                                    style = MaterialTheme.typography.caption,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                                )
                            }

                            if (status == FeedbackStatus.ERROR) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = stringResource(id = R.string.feedback_error),
                                    color = MaterialTheme.colors.error,
                                    style = MaterialTheme.typography.body2
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    viewModel.submitFeedback(
                                        appVersion = appVersion,
                                        buildNumber = buildNumber
                                    )
                                },
                                enabled = status != FeedbackStatus.LOADING,
                                shape = MaterialTheme.shapes.medium.copy(all = CornerSize(12.dp)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = primaryDarkColor)
                            ) {
                                if (status == FeedbackStatus.LOADING) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.Black,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.feedback_send_button),
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
