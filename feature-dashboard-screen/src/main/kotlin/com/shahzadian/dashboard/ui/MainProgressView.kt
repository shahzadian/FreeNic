package com.shahzadian.dashboard.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.theme.AppTypography
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

private const val ANIMATION_DURATION: Int = 1000

@Composable
internal fun MainProgressView(
    data: DashboardViewState.MainProgressUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(android.graphics.Color.parseColor("#00BFA5")),
                        Color(android.graphics.Color.parseColor("#3BAFDA")),
                    ),
                ),
                shape = MaterialTheme.shapes.large
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        NoCigDaysView(
            data = data,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        SavedMoneyView(
            data = data,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(R.string.dashboard_main_progress_footer),
            style = AppTypography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun NoCigDaysView(
    data: DashboardViewState.MainProgressUiModel,
    modifier: Modifier = Modifier,
) {
    val noCigDaysAnimated = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        noCigDaysAnimated.snapTo(0f)
        noCigDaysAnimated.animateTo(
            data.noCigDays.toFloat(),
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
            )
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = noCigDaysAnimated.value.toInt().toString(),
            style = AppTypography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = pluralStringResource(
                R.plurals.dashboard_main_progress_no_cig_days,
                data.noCigDays
            ),
            style = AppTypography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun SavedMoneyView(
    data: DashboardViewState.MainProgressUiModel,
    modifier: Modifier = Modifier,
) {
    val savedMoneyAnimated = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        savedMoneyAnimated.snapTo(0f)
        savedMoneyAnimated.animateTo(
            data.savedMoney,
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
            )
        )
    }
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val displayableCurrency = data.currency ?: ""
        val displayableSavedMoney = if (savedMoneyAnimated.value == data.savedMoney) {
            savedMoneyAnimated.value
        } else {
            savedMoneyAnimated.value.toInt()
        }
        Text(
            text = "${displayableSavedMoney}$displayableCurrency",
            style = AppTypography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 2.dp),
        )
        Text(
            text = stringResource(R.string.dashboard_main_progress_money_saved),
            style = AppTypography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
