package ru.taxcom.composeUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.taxcom.taxcomcomposeui.R

@Preview
@Composable
fun ShowPreviewBanner() {
    FullWidthBanner(
        R.drawable.ic_cashier,
        "Если у Вас есть вопросы\nи предложения по работе\nмобильного приложения,\nнапишите нам.",
        R.color.PrimaryForegroundColor,
        R.dimen.circular_progress_bar_size
    )
}

@Composable
fun FullWidthBanner(
    iconId: Int, title: String, backgroundColor: Int, iconSizeId: Int
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(dimensionResource(id = ru.taxcom.taxcomcomposeui.R.dimen.ml_dimen)),
    backgroundColor = colorResource(backgroundColor),
    shape = RoundedCornerShape(dimensionResource(id = ru.taxcom.taxcomcomposeui.R.dimen.sm_dimen)),
) {
    Row(
        modifier = Modifier.padding(dimensionResource(id = ru.taxcom.taxcomcomposeui.R.dimen.ml_dimen)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(id = iconSizeId))
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = ru.taxcom.taxcomcomposeui.R.dimen.ml_dimen)))
        Text(
            text = title,
            color = colorResource(R.color.white),
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(R.font.roboto_regular, weight = FontWeight.Normal)
            )
        )
    }
}