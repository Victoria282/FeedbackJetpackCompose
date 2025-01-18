package ru.taxcom.composeUI

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.taxcom.taxcomcomposeui.R
import ru.taxcom.utils.Utils.isCorrectEmail

@Preview
@Composable
fun PreviewEmailInputField() {
    EmailInputField {}
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailInputField(onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val isValidEmail = email.text.isCorrectEmail()

    OutlinedTextField(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.sm_dimen)),
        value = email,
        onValueChange = {
            email = it
            onValueChange.invoke(it.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.ml_dimen))
            .onFocusChanged {
                isFocused = it.isFocused
            },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.PrimaryForegroundColor),
            unfocusedBorderColor = colorResource(id = R.color.secondary_to_third_color_description),
            cursorColor = colorResource(id = R.color.PrimaryForegroundColor),
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        isError = !isValidEmail && email.text.isNotEmpty(),
        placeholder = {
            Text(
                text = stringResource(id = R.string.feedback_input_email_hint),
                color = colorResource(id = R.color.secondary_to_third_color_description)
            )
        },
        label = {
            Text(
                text = stringResource(id = R.string.feedback_input_email_title),
                color = colorResource(id = R.color.secondary_to_third_color_description)
            )
        },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = colorResource(id = R.color.SecondaryBackgroundColor),
            fontFamily = FontFamily(
                Font(R.font.roboto_regular, weight = FontWeight.Normal)
            )
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                isFocused = false
                keyboardController?.hide()
            }
        )
    )
}