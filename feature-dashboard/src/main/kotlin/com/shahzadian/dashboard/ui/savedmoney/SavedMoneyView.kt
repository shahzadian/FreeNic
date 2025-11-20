package com.shahzadian.dashboard.ui.savedmoney

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.dashboard.R
import com.shahzadian.dashboard.ui.ANIMATION_DURATION_INCREASE_DASHBOARD
import com.shahzadian.dashboard.ui.DashboardCard
import com.shahzadian.dashboard.ui.savedmoney.model.SavedMoneyUiModel
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun SavedMoneyView(
    savedMoneyUiModel: SavedMoneyUiModel,
    modifier: Modifier = Modifier,
) {
    DashboardCard(
        modifier = modifier,
    ) {
        Column(
            modifier = it,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_dashboard_saved_money),
                contentDescription = "saved money",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.dashboard_saved_money_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AmountView(
                savedMoney = savedMoneyUiModel.savedMoney,
                modifier = Modifier,
                currency = savedMoneyUiModel.currency,
            )
        }
    }
}

@Composable
private fun AmountView(
    savedMoney: Int,
    modifier: Modifier = Modifier,
    currency: String? = null,
) {
    val animatedValue = remember { Animatable(0f) }
    val firstLaunch = remember { mutableStateOf(true) }
    LaunchedEffect(savedMoney) {
        if (firstLaunch.value) {
            animatedValue.snapTo(0f)
            firstLaunch.value = false
        }
        animatedValue.animateTo(
            targetValue = savedMoney.toFloat(),
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION_INCREASE_DASHBOARD,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val amountText = if (savedMoney > 0) {
        max(1, animatedValue.value.roundToInt())
    } else 0
    val displayText = currency?.let { "$currency$amountText" } ?: amountText.toString()
    Text(
        text = displayText.toString(),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}