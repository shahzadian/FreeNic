package com.shahzadian.apprate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.noIndicationClick
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRateBottomSheet(
    onDismissRequest: () -> Unit,
    onRated: (starsCount: Int) -> Unit,
    onNotNowClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissRequest = remember {
        { onDismissRequest() }
    }
    val rated: (Int) -> Unit = remember { { onRated(it) } }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    ModalBottomSheet(
        onDismissRequest = dismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        ContentView(
            onRated = rated,
            onNotNowClicked = onNotNowClicked,
        )
    }
}

@Composable
private fun ContentView(
    onRated: (starsCount: Int) -> Unit,
    onNotNowClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var starsCount by remember { mutableStateOf(0) }
    val submitClick = remember { { onRated(starsCount) } }
    val notNowClick = remember { { onNotNowClicked() } }
    Column(
        modifier = modifier
            .padding(16.dp),
    ) {
        HeaderView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )
        StarsView(
            onRated = {
                starsCount = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )
        FooterView(
            onSubmitClicked = submitClick,
            onNotNowClicked = notNowClick,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.app_rate_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = stringResource(R.string.app_rate_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun StarsView(
    onRated: (starsCount: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentRating by remember { mutableStateOf(-1) }
    Row(
        modifier = modifier,
    ) {
        repeat(5) { index ->
            Image(
                painter = painterResource(com.shahzadian.apprate.R.drawable.ic_app_rate_star),
                contentDescription = "Star $index",
                modifier = Modifier
                    .weight(1f)
                    .noIndicationClick {
                        currentRating = index
                        onRated(currentRating + 1)
                    },
                colorFilter = ColorFilter.tint(
                    color = if (index <= currentRating) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.2f
                        )
                    }
                )
            )
        }
    }
}

@Composable
private fun FooterView(
    onSubmitClicked: () -> Unit,
    onNotNowClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val submitClick = remember { { onSubmitClicked() } }
    val notNowClick = remember { { onNotNowClicked() } }
    Column(
        modifier = modifier,
    ) {
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.app_rate_submit_button),
            onClick = submitClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.app_rate_cancel_button),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .noIndicationClick(onClick = notNowClick)
                .align(Alignment.CenterHorizontally),
        )
    }
}
