package ru.taxcom.feedback.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.taxcom.composeUI.RedCircleWithText
import ru.taxcom.feedback.R
import ru.taxcom.feedback.data.ui.FeedbackCardItem

@Preview
@Composable
fun PreviewFeedbackCardList() {
    FeedbackCard(
        FeedbackCardItem(
            R.string.create_applying_text, R.drawable.create_applying_icon, {}, 5
        )
    )
}

@Composable
fun CreateFeedbackCards(cards: List<FeedbackCardItem>) = Column {
    cards.forEach { card ->
        FeedbackCard(card)
    }
}

@Composable
fun FeedbackCard(card: FeedbackCardItem) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clickable(
            onClick = { card.callback.invoke() },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
        .padding(
            start = dimensionResource(id = R.dimen.ml_dimen),
            end = dimensionResource(id = R.dimen.ml_dimen),
            bottom = dimensionResource(id = R.dimen.sm_dimen)
        ),
    backgroundColor = colorResource(R.color.PrimaryBackgroundColor),
    elevation = dimensionResource(id = R.dimen.xxs_dimen),
    shape = RoundedCornerShape(dimensionResource(id = R.dimen.xs_dimen)),
) {
    Row(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.ml_dimen)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = card.image),
            contentDescription = null,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.xxs_dimen)))
        Text(
            text = stringResource(id = card.title),
            color = colorResource(R.color.SecondaryBackgroundColor),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        card.notifications?.let { count ->
            RedCircleWithText(
                count.toString(), colorResource(id = R.color.AccentColor)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = null,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(start = dimensionResource(id = R.dimen.ml_dimen))
        )
    }
}