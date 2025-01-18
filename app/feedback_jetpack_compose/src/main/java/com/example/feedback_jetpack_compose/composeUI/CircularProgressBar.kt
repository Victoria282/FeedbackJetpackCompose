package ru.taxcom.composeUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import ru.taxcom.taxcomcomposeui.R

@Preview(showBackground = true)
@Composable
fun PreviewCircularProgressBar() {
    CircularProgressBar(Modifier)
}

@Composable
fun CircularProgressBar(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .height(dimensionResource(id = R.dimen.circular_progress_bar_size))
            .width(dimensionResource(id = R.dimen.circular_progress_bar_size))
            .background(Color.Transparent),
        color = colorResource(id = R.color.PrimaryForegroundColor),
        strokeWidth = dimensionResource(id = R.dimen.xxxs_dimen)
    )
}