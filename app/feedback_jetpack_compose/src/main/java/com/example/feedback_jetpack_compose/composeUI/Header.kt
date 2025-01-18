package ru.taxcom.composeUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.taxcom.taxcomcomposeui.R

@Preview
@Composable
fun ShowChatHeader() {
    Header(
        "Чт, 07.11.2024г",
        Alignment.Center,
        bottomMarginId = R.dimen.ml_dimen,
        startMarginId = R.dimen.zero_dimen,
        topMarginId = R.dimen.zero_dimen,
        endMarginId = R.dimen.zero_dimen
    )
}

@Composable
fun Header(
    title: String,
    alignment: Alignment,
    bottomMarginId: Int,
    startMarginId: Int,
    topMarginId: Int,
    endMarginId: Int
) = Box(Modifier.fillMaxWidth()) {
    Text(
        text = title,
        color = colorResource(R.color.secondary_to_third_color_description),
        fontSize = 14.sp,
        modifier = Modifier
            .padding(
                bottom = dimensionResource(id = bottomMarginId),
                start = dimensionResource(id = startMarginId),
                top = dimensionResource(id = topMarginId),
                end = dimensionResource(id = endMarginId)
            )
            .wrapContentHeight()
            .wrapContentWidth()
            .align(alignment),
        fontFamily = FontFamily(
            Font(R.font.roboto_regular, weight = FontWeight.Normal)
        )
    )
}