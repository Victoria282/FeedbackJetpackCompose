package ru.taxcom.composeUI

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.taxcom.taxcomcomposeui.R

@Composable
@Preview
fun ShowRedCircleWithText() {
    RedCircleWithText("5", Color.Red)
}

@Composable
fun RedCircleWithText(text: String, color: Color) = Box(
    modifier = Modifier.size(dimensionResource(id = R.dimen.xxl_dimen)),
    contentAlignment = Alignment.Center
) {
    Canvas(modifier = Modifier.matchParentSize()) {
        drawCircle(color)
    }
    Text(
        text = text, color = Color.White, fontSize = 12.sp
    )
}