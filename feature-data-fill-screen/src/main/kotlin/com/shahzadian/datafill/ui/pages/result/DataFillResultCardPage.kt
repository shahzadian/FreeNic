package com.shahzadian.datafill.ui.pages.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.datafill.ui.DataFillViewState

@Composable
internal fun DataFillResultCardPage(
    userData: DataFillViewState.Result.UserData,
    modifier: Modifier = Modifier,
) {
    CardView(
        userData = userData,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun CardView(
    userData: DataFillViewState.Result.UserData,
    modifier: Modifier = Modifier,
) {
    val dividerHeight = 12.dp
    Column(
        modifier = modifier
            .clip(
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
            .padding(16.dp),
    ) {
        CardTitleRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextRow(
            leftText = TextWrapper.FromRes(R.string.data_fill_result_card_name),
            rightText = TextWrapper.FromString(userData.name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dividerHeight)
        )
        DividerLine(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dividerHeight)
        )
        TextRow(
            leftText = TextWrapper.FromRes(R.string.data_fill_result_card_age),
            rightText = TextWrapper.FromString(userData.age),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dividerHeight)
        )
        DividerLine(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dividerHeight)
        )
        TextRow(
            leftText = TextWrapper.FromRes(R.string.data_fill_result_card_daily_cigarettes),
            rightText = TextWrapper.FromString(userData.cigarettesPerDay),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun CardTitleRow(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.data_fill_result_card_title),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        Image(
            painter = painterResource(com.shahzadian.datafill.R.drawable.ic_data_fill_result_card),
            contentDescription = "data fill result card",
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            alignment = Alignment.CenterEnd,
        )
    }
}

@Composable
private fun TextRow(
    leftText: TextWrapper,
    rightText: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = textFromWrapper(leftText),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = textFromWrapper(rightText),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun DividerLine(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outline)

    )
}