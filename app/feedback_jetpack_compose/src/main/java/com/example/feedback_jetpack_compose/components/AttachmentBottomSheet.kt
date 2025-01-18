package ru.taxcom.feedback.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.taxcom.composeUI.SampleToolbar
import ru.taxcom.feedback.R
import com.example.feedback_jetpack_compose.utils.MAX_COUNT_OF_SELECTED_ATTACHMENTS

@Preview
@Composable
fun PreviewAttachmentBottomSheet() {
    AttachmentBottomSheet(Modifier, images = listOf(), listOf(), {}, MAX_COUNT_OF_SELECTED_ATTACHMENTS, {})
}

@Composable
fun AttachmentBottomSheet(
    modifier: Modifier,
    images: List<Uri>,
    selectedImages: List<Uri>,
    onCloseButtonClick: () -> Unit,
    maxCountOfSelectedAttachments: Int,
    onAttachButtonClick: (images: List<Uri>) -> Unit
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(0.65f)
        .background(colorResource(id = R.color.PrimaryBackgroundColor))
) {
    val indexesOfSelectedImages = remember { mutableStateOf(selectedImages) }
    Row {
        SampleToolbar(
            stringResource(R.string.feedback_attachment_title),
            onCloseButtonClick,
            R.drawable.ic_clear
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), modifier = Modifier
            .wrapContentHeight()
            .weight(1f)
            .padding(
                start = dimensionResource(id = R.dimen.m_dimen),
                end = dimensionResource(id = R.dimen.m_dimen)
            )
    ) {
        items(images.size) { index ->
            val imageUri = images[index]
            Box(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.xxs_dimen))
                    .height(dimensionResource(id = R.dimen.feedback_attachment_height))
            ) {
                AsyncImage(model = imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.s_dimen)))
                        .fillMaxWidth(1f)
                        .clickable {
                            val currentSelectedList = indexesOfSelectedImages.value.toMutableList()
                            if (currentSelectedList.contains(imageUri)) currentSelectedList.remove(
                                imageUri
                            )
                            else {
                                if(currentSelectedList.size >= maxCountOfSelectedAttachments) return@clickable
                                currentSelectedList.add(imageUri)
                            }
                            indexesOfSelectedImages.value = currentSelectedList.toList()
                        })
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(dimensionResource(id = R.dimen.xxs_dimen))
                ) {
                    val isSelected = indexesOfSelectedImages.value.contains(imageUri)
                    if (isSelected) {
                        val selectedIndex = indexesOfSelectedImages.value.indexOf(imageUri) + 1
                        FullCircle(selectedIndex.toString())
                    } else EmptyCircle()
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.s_dimen)))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.xxxl_dimen))
            .padding(
                start = dimensionResource(id = R.dimen.xxxl_dimen),
                end = dimensionResource(id = R.dimen.xxxl_dimen)
            )
    ) {
        Button(
            onClick = { onAttachButtonClick.invoke(indexesOfSelectedImages.value) },
            modifier = Modifier
                .fillMaxSize()
                .height(dimensionResource(id = R.dimen.xxxl_dimen)),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.PrimaryForegroundColor))
        ) {
            Text(
                stringResource(id = R.string.add_pictures_button_text),
                color = colorResource(id = R.color.white)
            )
        }
    }
    Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.s_dimen)))
}

@Composable
fun EmptyCircle() = Surface(
    modifier = Modifier
        .size(dimensionResource(id = R.dimen.ml_dimen))
        .background(
            color = colorResource(R.color.PrimaryBackgroundColor),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.ml_dimen))
        ),
    shape = RoundedCornerShape(dimensionResource(id = R.dimen.ml_dimen)),
    border = BorderStroke(
        dimensionResource(id = R.dimen.unit_dimen),
        colorResource(id = R.color.FourthForegroundColor)
    )
) {}

@Composable
fun FullCircle(count: String) = Box(
    modifier = Modifier
        .size(dimensionResource(id = R.dimen.ml_dimen))
        .background(
            color = colorResource(R.color.PrimaryForegroundColor), shape = CircleShape
        ), contentAlignment = Alignment.Center
) {
    Text(
        text = count,
        color = colorResource(R.color.white),
        fontSize = 12.sp,
        fontFamily = FontFamily(
            Font(R.font.roboto_regular, weight = FontWeight.Normal)
        )
    )
}

@Preview
@Composable
fun PreviewEmptyCircle() {
    EmptyCircle()
}

@Preview
@Composable
fun PreviewFullCircle() {
    FullCircle("1")
}