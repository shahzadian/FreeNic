package com.shahzadian.distractionactivityscreen.guessshape.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel.GuessShapeViewState

@Composable
internal fun GuessShapeView(
    shapes: List<GuessShapeViewState.ShapeUiModel>,
    onShapeSelected: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shapeSelected = remember { { id: String -> onShapeSelected(id) } }
    var selectedShapeId by remember { mutableStateOf("") }
    var isButtonEnabled by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        HeaderView(
            modifier = Modifier.padding(bottom = 16.dp),
        )
        ShapesView(
            shapes = shapes,
            onShapeClicked = {
                selectedShapeId = it
                isButtonEnabled = shapes.any { shape -> shape.id == selectedShapeId }
            },
            modifier = Modifier.padding(bottom = 16.dp),
        )
        ConfirmButton(
            isEnabled = isButtonEnabled,
            onButtonClick = {
                shapeSelected(selectedShapeId)
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.guess_shape_guessing_title),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun ShapesView(
    shapes: List<GuessShapeViewState.ShapeUiModel>,
    onShapeClicked: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shapeClicked: (String) -> Unit = remember { { id -> onShapeClicked(id) } }
    var selectedShapeId by remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        shapes.forEach { shape ->
            ShapeView(
                shape = shape,
                isSelected = selectedShapeId == shape.id,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clickable {
                        selectedShapeId = shape.id
                        shapeClicked(shape.id)
                    },
            )
        }
    }
}

@Composable
private fun ShapeView(
    shape: GuessShapeViewState.ShapeUiModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small),
    ) {
        Image(
            painter = imageFromWrapper(shape.icon),
            contentDescription = shape.id,
            colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
        )
    }
}

@Composable
private fun ConfirmButton(
    isEnabled: Boolean,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MainSubmitButton(
        text = TextWrapper.FromRes(R.string.guess_shape_guessing_submit),
        onClick = onButtonClick,
        enabled = isEnabled,
        modifier = modifier,
    )
}
