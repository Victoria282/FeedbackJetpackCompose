package ru.taxcom.composeUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun PreviewToolbar() {
    SampleToolbar("История обращений", {})
}

@Composable
fun SampleToolbar(
    title: String, onButtonClick: () -> Unit, iconId: Int? = null
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.ml_dimen)),
    verticalAlignment = Alignment.CenterVertically
) {
    Image(
        painter = painterResource(id = iconId ?: R.drawable.filer_ic_back),
        contentDescription = null,
        modifier = Modifier.clickable(onClick = onButtonClick)
    )
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.sm_dimen)))
    Text(
        text = title,
        fontSize = 20.sp,
        color = colorResource(R.color.SecondaryBackgroundColor),
        modifier = Modifier.weight(1f),
        fontFamily = FontFamily(
            Font(R.font.roboto_bold, weight = FontWeight.Normal)
        )
    )
}