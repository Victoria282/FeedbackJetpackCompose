package ru.taxcom.feedback.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.taxcom.composeUI.FullScreenImageDialog
import ru.taxcom.feedback.R
import ru.taxcom.feedback.data.ui.ChatUI
import ru.taxcom.feedback.data.ui.ImageUI
import ru.taxcom.feedback.data.ui.MessagePosition
import ru.taxcom.feedback.data.ui.MessageUI

@Preview
@Composable
fun PreviewCloudMessage() {
    CloudMessage(
        ImageUI(
            time = "14:09",
            image = "",
            date = "Ср, 09.10.2024",
            position = MessagePosition.RIGHT
        )
    )
}

@Composable
fun CloudMessage(message: ChatUI) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.ml_dimen))
) {
    val alignment = when (message.position) {
        MessagePosition.RIGHT -> AbsoluteAlignment.Right
        MessagePosition.LEFT -> AbsoluteAlignment.Left
    }
    val messageColor = when (message.position) {
        MessagePosition.RIGHT -> R.color.PrimaryForegroundColor
        MessagePosition.LEFT -> R.color.PrimaryBackgroundColor
    }
    val textColor = when (message.position) {
        MessagePosition.RIGHT -> R.color.white
        MessagePosition.LEFT -> R.color.SecondaryBackgroundColor
    }

    when (message) {
        is MessageUI -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(alignment),
                backgroundColor = colorResource(id = messageColor),
                shape = RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.sm_dimen),
                    topEnd = dimensionResource(id = R.dimen.sm_dimen),
                    bottomEnd = if (message.position == MessagePosition.RIGHT) dimensionResource(id = R.dimen.zero_dimen)
                    else dimensionResource(id = R.dimen.sm_dimen),
                    bottomStart = if (message.position == MessagePosition.RIGHT) dimensionResource(
                        id = R.dimen.sm_dimen
                    )
                    else dimensionResource(id = R.dimen.zero_dimen)
                ),
                elevation = dimensionResource(id = R.dimen.zero_dimen)
            ) {
                Column {
                    Text(
                        text = message.message,
                        color = colorResource(textColor),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular, weight = FontWeight.Normal)
                        ),
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.ml_dimen),
                            end = dimensionResource(id = R.dimen.s_dimen),
                            top = dimensionResource(id = R.dimen.sm_dimen),
                            bottom = dimensionResource(id = R.dimen.xxs_dimen)
                        )
                    )

                    Text(
                        text = message.time,
                        color = colorResource(textColor),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular, weight = FontWeight.Normal)
                        ),
                        modifier = Modifier
                            .padding(
                                end = dimensionResource(id = R.dimen.sm_dimen),
                                bottom = dimensionResource(id = R.dimen.sm_dimen)
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        is ImageUI -> Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.5f)
                .align(alignment),
            backgroundColor = colorResource(id = messageColor),
            shape = RoundedCornerShape(
                topStart = dimensionResource(id = R.dimen.sm_dimen),
                topEnd = dimensionResource(id = R.dimen.sm_dimen),
                bottomEnd = if (message.position == MessagePosition.RIGHT) dimensionResource(id = R.dimen.zero_dimen)
                else dimensionResource(id = R.dimen.sm_dimen),
                bottomStart = if (message.position == MessagePosition.RIGHT) dimensionResource(
                    id = R.dimen.sm_dimen
                )
                else dimensionResource(id = R.dimen.zero_dimen)
            ),
            elevation = dimensionResource(id = R.dimen.zero_dimen)
        ) {
            Base64Image(message.image)
        }
    }
    if (message.position == MessagePosition.RIGHT) {
        val rightArrowColor = colorResource(id = R.color.PrimaryForegroundColor)
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(alignment)
        ) {
            Canvas(
                modifier = Modifier
                    .size(16.dp)
                    .align(AbsoluteAlignment.BottomRight)
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(path = path, color = rightArrowColor)
            }
        }
    }
    if (message.position == MessagePosition.LEFT) {
        val leftArrowColor = colorResource(id = R.color.PrimaryBackgroundColor)
        Canvas(modifier = Modifier.size(16.dp)) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path = path, color = leftArrowColor)
        }
    }
}

@Composable
fun Base64Image(base64String: String) {
    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    var isNeedFullScreen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .padding(dimensionResource(id = R.dimen.xxs_dimen))
            .fillMaxHeight(0.6f)
            .clickable { isNeedFullScreen = true }
            .clip(RoundedCornerShape(dimensionResource(id = ru.taxcom.taxcomcomposeui.R.dimen.s_dimen)))
    ) {
        if (isNeedFullScreen) {
            FullScreenImageDialog(bitmap) { isNeedFullScreen = false }
        }
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.Fit
        )
    }
}