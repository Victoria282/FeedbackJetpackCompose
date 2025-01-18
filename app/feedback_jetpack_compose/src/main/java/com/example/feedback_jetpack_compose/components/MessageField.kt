package ru.taxcom.feedback.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.taxcom.composeUI.FullScreenImageDialog
import ru.taxcom.taxcomcomposeui.R
import ru.taxcom.utils.Utils.isCorrectEmail
import ru.taxcom.feedback.R as FeedbackR

@Preview
@Composable
fun PreviewMessageField() {
    MessageField({}, emptyList(), {}, {}, {}, Modifier, "", true)
}

@Composable
fun MessageField(
    onMessageChange: (String) -> Unit,
    attachments: List<Uri>,
    onSendClick: () -> Unit,
    onAttachClick: () -> Unit,
    onRemoveAttachment: (Uri) -> Unit,
    modifier: Modifier,
    email: String? = null,
    possibilityToAttach: Boolean
) = Column(
    modifier
        .background(
            color = colorResource(id = R.color.screen_background_gray_color)
        )
) {
    var attachmentList = attachments
    var message by remember { mutableStateOf("") }
    var isNeedFullScreen by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.xxs_dimen),
                start = dimensionResource(id = R.dimen.ml_dimen),
                end = dimensionResource(id = R.dimen.ml_dimen),
                bottom = dimensionResource(id = R.dimen.s_dimen)
            ), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.clip_document_icon),
            contentDescription = null,
            modifier = Modifier.clickable {
                onAttachClick.invoke()
            },
            colorFilter = ColorFilter.tint(
                colorResource(id = if (possibilityToAttach) R.color.PrimaryForegroundColor else R.color.SecondaryForegroundColor)
            )
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.xxxs_dimen)))
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.s_dimen))),
            backgroundColor = colorResource(R.color.PrimaryBackgroundColor),
            elevation = dimensionResource(id = R.dimen.zero_dimen),
        ) {
            Column {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), modifier = Modifier
                        .wrapContentHeight()
                        .padding(
                            start = dimensionResource(id = R.dimen.s_dimen),
                            end = dimensionResource(id = R.dimen.s_dimen),
                            bottom = dimensionResource(id = R.dimen.xxxs_dimen),
                            top = dimensionResource(id = R.dimen.xxxs_dimen)
                        )
                ) {
                    items(attachmentList.size) { index ->
                        Box(modifier = Modifier.size(dimensionResource(id = FeedbackR.dimen.feedback_attachment_message_height))) {
                            val imageUri = attachmentList[index]
                            AsyncImage(
                                model = imageUri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(dimensionResource(id = FeedbackR.dimen.feedback_attachment_message_height))
                                    .padding(
                                        start = dimensionResource(id = R.dimen.xxxs_dimen),
                                        end = dimensionResource(id = R.dimen.xxxs_dimen),
                                        top = dimensionResource(id = R.dimen.xxs_dimen)
                                    )
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.s_dimen)))
                                    .fillMaxWidth(1f)
                                    .clickable { isNeedFullScreen = true }
                            )
                            if (isNeedFullScreen) {
                                FullScreenImageDialog(imageUri = imageUri) {
                                    isNeedFullScreen = false
                                }
                            }
                            Image(
                                painter = painterResource(id = ru.taxcom.feedback.R.drawable.reset_attachment_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .align(Alignment.TopEnd)
                                    .clickable {
                                        onRemoveAttachment.invoke(imageUri)
                                    }
                            )
                        }
                    }
                }
                TextField(
                    value = message,
                    onValueChange = {
                        message = it
                        onMessageChange.invoke(it)
                    },
                    modifier = Modifier
                        .border(
                            dimensionResource(id = R.dimen.zero_dimen), Color.Transparent
                        )
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(id = R.color.SecondaryBackgroundColor),
                        backgroundColor = colorResource(id = R.color.PrimaryBackgroundColor),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.PrimaryForegroundColor)
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.hint_of_chat_field),
                            color = colorResource(id = R.color.secondary_to_third_color_description)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.SecondaryBackgroundColor),
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular, weight = FontWeight.Normal)
                        )
                    ),
                    singleLine = false,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.zero_dimen)),
                )
            }
        }
        if (message.isNotEmpty() || attachmentList.isNotEmpty()) {
            if (email == null || (email.isEmpty().not() && email.isCorrectEmail())) {
                Image(
                    painter = painterResource(id = R.drawable.send_message_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onSendClick.invoke()
                            attachmentList = emptyList()
                            message = ""
                        }
                        .padding(
                            start = dimensionResource(id = R.dimen.xxs_dimen)
                        ))
            }
        }
    }
}