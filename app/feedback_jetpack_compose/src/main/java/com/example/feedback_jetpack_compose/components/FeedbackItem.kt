package ru.taxcom.feedback.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.taxcom.feedback.R
import ru.taxcom.feedback.data.FeedbackHistoryItem

@Preview
@Composable
fun PreviewFeedbackHistoryItem() {
    FeedbackHistoryItem(
        FeedbackHistoryItem(
            282,
            topic = "Кассы не работают, сделайте пожалуйста что-то..",
            closed = false,
            readed = false
        )
    ) {}
}

@Composable
fun FeedbackHistoryItem(item: FeedbackHistoryItem, callback: (Int?) -> Unit) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clickable(
            onClick = { callback(item.id) },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
        .padding(
            start = dimensionResource(id = R.dimen.ml_dimen),
            end = dimensionResource(id = R.dimen.ml_dimen),
            bottom = dimensionResource(id = R.dimen.sm_dimen)
        ),
    backgroundColor = colorResource(R.color.PrimaryBackgroundColor),
    elevation = dimensionResource(R.dimen.xxxs_dimen),
    shape = RoundedCornerShape(dimensionResource(R.dimen.s_dimen))) {
    Column {
        Row(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.ml_dimen),
                top = dimensionResource(id = R.dimen.ml_dimen),
                end = dimensionResource(id = R.dimen.ml_dimen)
            )
        ) {
            Text(
                text = stringResource(id = R.string.applying_number_text, item.id.toString()),
                color = colorResource(R.color.SecondaryBackgroundColor),
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(R.font.roboto_medium, weight = FontWeight.Normal)
                )
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        if (item.topic.isNullOrEmpty().not()) {
            Text(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.ml_dimen),
                    top = dimensionResource(id = R.dimen.xxs_dimen)
                ),
                text = item.topic ?: "",
                color = colorResource(R.color.ThirdForegroundColor),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular, weight = FontWeight.Normal)
                )
            )
        }
        Row(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.ml_dimen),
                top = dimensionResource(id = R.dimen.xxs_dimen),
                end = dimensionResource(id = R.dimen.ml_dimen)
            )
        ) {
            val backgroundColorId =
                if (item.closed == true) R.color.feedback_closed_background_color else R.color.feedback_opened_background_color
            val textColorId =
                if (item.closed == true) R.color.feedback_closed_text_color else R.color.feedback_opened_text_color

            val textId =
                if (item.closed == true) R.string.feedback_in_ready else R.string.feedback_in_process
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { callback(item.id) }
                    )
                    .padding(bottom = dimensionResource(id = R.dimen.ml_dimen)),
                elevation = dimensionResource(R.dimen.zero_dimen),
                shape = RoundedCornerShape(dimensionResource(R.dimen.xs_dimen)),
                backgroundColor = colorResource(backgroundColorId)) {
                Text(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.ml_dimen),
                        end = dimensionResource(id = R.dimen.ml_dimen),
                        top = dimensionResource(id = R.dimen.xxs_dimen),
                        bottom = dimensionResource(id = R.dimen.xxs_dimen)
                    ),
                    text = stringResource(id = textId),
                    color = colorResource(textColorId),
                    fontSize = 16.sp
                )
            }
        }
    }
}