package ru.taxcom.composeUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.taxcom.taxcomcomposeui.R

@Preview
@Composable
fun ShowSampleMessage() {
    SampleMessage("Ошибка")
}

@Composable
fun SampleMessage(message: String) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Text(
        text = message,
        color = colorResource(R.color.secondary_to_third_color_description),
        fontSize = 16.sp,
        modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Center),
        fontFamily = FontFamily(
            Font(R.font.roboto_regular, weight = FontWeight.Normal)
        )
    )
}