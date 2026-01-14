package com.shahzadian.subscription.paywall.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewCommand
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewEvent
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewModel
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewState

@Composable
fun PaywallScreen(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val viewModel: PaywallViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    val backIconClickEvent = remember {
        { viewModel.onViewEvent(PaywallViewEvent.OnBackIconClicked) }
    }
    when (state) {
        is PaywallViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }
        is PaywallViewState.Data -> {
            ContentView(
                data = state as PaywallViewState.Data,
                onBackIconClicked = backIconClickEvent,
                modifier = modifier.fillMaxSize()
            )
        }
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel.viewCommandFlow.collect { command ->
            when (command) {
                is PaywallViewCommand.NavigateBack -> navigator.navigateBack()
            }
        }
    }
}

@Composable
private fun ContentView(
    data: PaywallViewState.Data,
    onBackIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember {
        { onBackIconClicked() }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.paywall_page_title),
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
        modifier = modifier,
    ) {
        HeaderView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        SubscriptionPlansView(
            plans = data.subscriptionPlans,
            defaultPlanIndex = data.defaultSelectedPlanIndex,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        PaidFeaturesBlock(
            paidFeatures = data.paidFeatures,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(com.shahzadian.subscription.R.drawable.ic_paywall_header),
            contentDescription = "paywall header",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.paywall_header_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(R.string.paywall_header_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun PaidFeaturesBlock(
    paidFeatures: List<PaywallViewState.PaidFeatureUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.paywall_features_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        PaidFeaturesCarousel(
            paidFeatures = paidFeatures,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun PaidFeaturesCarousel(
    paidFeatures: List<PaywallViewState.PaidFeatureUiModel>,
    modifier: Modifier = Modifier,
) {
    val actualCount = paidFeatures.size
    val infiniteCount = Int.MAX_VALUE
    val initialPageIndex = 0
    val pagerState = rememberPagerState(initialPage = initialPageIndex) {
        infiniteCount
    }
    var currentPageIndex by remember { mutableStateOf(initialPageIndex) }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                currentPageIndex = page % actualCount
            }
    }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.Top,
            pageSpacing = 8.dp
        ) { page ->
            val actualIndex = page % actualCount
            PaidFeatureView(
                uiModel = paidFeatures[actualIndex],
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            repeat(paidFeatures.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .background(
                            color = if (currentPageIndex == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outline
                            },
                            shape = CircleShape,
                        )
                        .size(10.dp)
                )
            }
        }
    }
}

@Composable
private fun SubscriptionPlansView(
    plans: List<PaywallViewState.SubscriptionPlanUiModel>,
    defaultPlanIndex: Int,
    modifier: Modifier = Modifier,
) {
    var selectedPlanIndex by remember { mutableIntStateOf(defaultPlanIndex) }
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.subscription_plan_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        plans.forEachIndexed { index, plan ->
            SubscriptionPlanView(
                plan = plan,
                modifier = Modifier.padding(bottom = if (index == plans.size - 1) 16.dp else 8.dp),
                selected = selectedPlanIndex == index,
                onClick = {
                    selectedPlanIndex = index
                }
            )
        }
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.profile_subscription_submit_button),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
