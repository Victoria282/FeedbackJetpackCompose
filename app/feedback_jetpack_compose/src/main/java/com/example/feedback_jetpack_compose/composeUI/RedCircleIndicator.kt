package ru.taxcom.composeUI

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import ru.taxcom.taxcomcomposeui.R

@Composable
@Preview
fun ShowRedCircleIndicator() {
    RedCircleIndicator(Color.Red)
}

@Composable
fun RedCircleIndicator(color: Color) = Box(
    modifier = Modifier.size(dimensionResource(id = R.dimen.m_dimen)),
    contentAlignment = Alignment.Center
) {
    Canvas(modifier = Modifier.matchParentSize()) {
        drawCircle(color)
    }
}