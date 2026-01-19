package com.shahzadian.widget

import android.content.Context
import android.content.Intent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.freenic.AppLaunchConst
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.theme.AppTypography
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import dagger.hilt.android.AndroidEntryPoint
import java.util.Currency
import javax.inject.Inject


class MainAppWidget(
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
) : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var hasPaidSubscription: Boolean? by remember { mutableStateOf(null) }
            var userData: UserData? by remember { mutableStateOf(null) }
            var userDataHelper: UserDataHelper? by remember { mutableStateOf(null) }
            LaunchedEffect(Unit) {
                // Check subscription first
                hasPaidSubscription =
                    getUserCurrentSubscriptionUseCase() is UserSubscriptionStatus.HasSubscription
                if (hasPaidSubscription == true) {
                    userData = getUserDataUseCase()
                        ?: throw IllegalStateException("UserData is null")
                    userDataHelper = userDataHelperFactory.create(userData!!)
                }
            }
            FreeNicTheme {
                when {
                    hasPaidSubscription == false -> {
                        UpgradeWidget()
                    }

                    userData != null && userDataHelper != null -> {
                        MainWidget(
                            userData = userData!!,
                            userDataHelper = userDataHelper!!
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MainWidget(
    userData: UserData,
    userDataHelper: UserDataHelper,
) {
    val context = LocalContext.current
    val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        ?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    var modifier = GlanceModifier
        .fillMaxSize()
        .background(
            color = MaterialTheme.colorScheme.background,
        )
        .padding(16.dp)
    if (launchIntent != null) {
        modifier = modifier.clickable(actionStartActivity(launchIntent))
    }
    Column(
        modifier = modifier,
        verticalAlignment = Alignment.Vertical.Top,
        horizontalAlignment = Alignment.Horizontal.Start
    ) {
        HeaderView(
            modifier = GlanceModifier
        )
        Column {
            DataView(
                userDataHelper = userDataHelper,
                modifier = GlanceModifier.padding(end = 4.dp)
            )
            Box(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(ColorProvider(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)))

            ) { }
            FooterView(
                userData = userData,
                userDataHelper = userDataHelper,
                modifier = GlanceModifier
                    .padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun UpgradeWidget() {
    val context = LocalContext.current
    val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        ?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.putExtra(AppLaunchConst.KEY_INTENT_TARGET_SCREEN, AppScreen.PaywallScreen.route)
        }
    var modifier = GlanceModifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(16.dp)
    if (launchIntent != null) {
        modifier = modifier.clickable(actionStartActivity(launchIntent))
    }
    Column(
        modifier = modifier,
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Text(
            text = context.getString(com.shahzadian.baseui.R.string.widget_main_upgrade),
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onPrimary),
                fontSize = AppTypography.bodyLarge.fontSize,
                fontWeight = FontWeight.Medium,
            ),
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )
        Button(
            text = context.getString(com.shahzadian.baseui.R.string.widget_main_upgrade_button),
            onClick = {
                launchIntent?.let { context.startActivity(it) }
            },
            colors = androidx.glance.ButtonDefaults.buttonColors(
                backgroundColor = ColorProvider(MaterialTheme.colorScheme.primary),
            ),
        )
        Text(
            text = context.getString(com.shahzadian.baseui.R.string.widget_main_info),
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onSecondary),
                fontSize = AppTypography.bodyMedium.fontSize,
            ),
            modifier = GlanceModifier
                .padding(top = 8.dp)
        )
    }
}

@Composable
private fun HeaderView(
    modifier: GlanceModifier = GlanceModifier
) {
    val context = LocalContext.current
    val label = context.getString(com.shahzadian.baseui.R.string.app_name)
    val textStyle = AppTypography.bodyMedium
    Text(
        text = label,
        style = TextStyle(
            color = ColorProvider(MaterialTheme.colorScheme.onSecondary),
            fontSize = textStyle.fontSize,
            fontWeight = FontWeight.Medium,
        ),
        modifier = modifier,
    )
}

@Composable
private fun DataView(
    userDataHelper: UserDataHelper,
    modifier: GlanceModifier = GlanceModifier
) {
    val noCigDays = userDataHelper.getSkippedDaysCount()
    val context = LocalContext.current
    val noCigDaysText = context.resources.getQuantityString(
        com.shahzadian.baseui.R.plurals.quantity_days,
        noCigDays,
        noCigDays
    )
    Column(
        modifier = modifier,
    ) {
        val noCigDaysStyle = AppTypography.headlineMedium
        Text(
            text = noCigDaysText,
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onPrimary),
                fontSize = noCigDaysStyle.fontSize,
                fontWeight = FontWeight.Medium,
            ),
            modifier = GlanceModifier.padding(bottom = 4.dp)
        )
        val smokeFreeStyle = AppTypography.bodyLarge
        Text(
            text = context.getString(com.shahzadian.baseui.R.string.widget_main_smoke_free),
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.primary),
                fontSize = smokeFreeStyle.fontSize,
            ),
            modifier = GlanceModifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
private fun FooterView(
    userData: UserData,
    userDataHelper: UserDataHelper,
    modifier: GlanceModifier = GlanceModifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_widget_main_saved_money),
            contentDescription = "Money saved",
            modifier = GlanceModifier.padding(end = 8.dp)
        )
        val context = LocalContext.current
        val savedMoneyText =
            context.getString(com.shahzadian.baseui.R.string.widget_main_info_money_saved)
        Text(
            text = savedMoneyText,
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onSecondary),
                fontSize = AppTypography.bodyMedium.fontSize,
            ),
            modifier = GlanceModifier.padding(end = 8.dp)
        )
        val displayableCurrency =
            try {
                Currency.getInstance(userData.cigarettesData.priceData.currency).symbol
            } catch (t: Throwable) {
                ""
            }
        Text(
            text = "${userDataHelper.getSavedMoney()}$displayableCurrency",
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onPrimary),
                fontSize = AppTypography.titleLarge.fontSize,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@AndroidEntryPoint
class FreeNicGlanceWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var userDataHelperFactory: UserDataHelperFactory

    @Inject
    lateinit var getUserDataUseCase: GetUserDataUseCase

    @Inject
    lateinit var getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase

    override val glanceAppWidget: GlanceAppWidget by lazyUnsafe {
        MainAppWidget(
            userDataHelperFactory = userDataHelperFactory,
            getUserDataUseCase = getUserDataUseCase,
            getUserCurrentSubscriptionUseCase = getUserCurrentSubscriptionUseCase,
        )
    }
}
