package com.shahzadian.datafill.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.datafill.ui.DataFillViewState

@Composable
internal fun DataFillCigarettesPerDayPagePage(
    data: DataFillViewState.CigarettesPerDay,
    onCigarettesCountChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        DataFillHeader(
            title = TextWrapper.FromRes(R.string.data_fill_smoking_habits_title),
            subtitle = TextWrapper.FromRes(R.string.data_fill_smoking_habits_subtitle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        )
        CigarettesCountBlock(
            cigarettesCount = data.cigarettesPerDay,
            packsPerDay = data.packsPerDay,
            range = data.cigarettesCountIntRange,
            onValueChange = { onCigarettesCountChanged(it.toString()) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CigarettesCountBlock(
    cigarettesCount: String,
    packsPerDay: String,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f)
            )
            .padding(16.dp),
    ) {
        DataFillInputLabel(
            text = TextWrapper.FromRes(R.string.data_fill_cigarettes_per_day_label),
            modifier = Modifier.padding(bottom = 8.dp),
        )
        DataFillInputDescription(
            text = TextWrapper.FromRes(R.string.data_fill_cigarettes_per_day_desc),
            modifier = Modifier.padding(bottom = 24.dp),
        )
        var sliderValue by remember {
            mutableFloatStateOf(
                cigarettesCount.toFloatOrNull() ?: range.first.toFloat()
            )
        }
        CigarettesCountCircle(
            cigarettesCount = sliderValue.toInt().toString(),
            packsPerDay = packsPerDay,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                onValueChange(it.toInt())
            },
            valueRange = range.first.toFloat()..range.last.toFloat(),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = range.first.toString(),
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = ((range.last - range.first) / 2 + 1).toString(),
                modifier = Modifier.align(Alignment.Center),
            )
            Text(
                text = range.last.toString(),
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun CigarettesCountCircle(
    cigarettesCount: String,
    packsPerDay: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val countCircleWidth = 160.dp
        val countCircleHeight = 160.dp
        Box(
            modifier = Modifier
                .width(countCircleWidth + 48.dp)
                .height(countCircleHeight + 48.dp)
                .weight(2f)
        ) {
            Box(
                modifier = Modifier
                    .width(countCircleWidth)
                    .height(countCircleHeight)
                    .clip(
                        shape = CircleShape,
                    )
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    .align(Alignment.Center)
                    .padding(16.dp),
            ) {
                Text(
                    text = cigarettesCount,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }
            Image(
                painter = painterResource(com.shahzadian.datafill.R.drawable.ic_data_fill_cigarettes_per_day_circle),
                contentDescription = "Cigarettes count",
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 10.dp,
                        end = 24.dp,
                    )
            )
        }

        PacksPerDayView(
            value = packsPerDay,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
        )

    }
}

@Composable
private fun PacksPerDayView(
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(R.string.data_fill_cigarettes_per_day_packs_per_day_suffix),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}