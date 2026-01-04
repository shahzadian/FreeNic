package com.shahzadian.widget

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.theme.AppTypography
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class MainAppWidget(
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
) : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var userData: UserData? by remember { mutableStateOf(null) }
            var userDataHelper: UserDataHelper? by remember { mutableStateOf(null) }
            LaunchedEffect(Unit) {
                userData = getUserDataUseCase()
                    ?: throw IllegalStateException("UserData is null")
                userDataHelper = userDataHelperFactory.create(userData!!)
            }
            userDataHelper?.let {
                FreeNicTheme {
                    MainWidget(
                        userDataHelper = it
                    )
                }
            }
        }
    }
}

@Composable
private fun MainWidget(
    userDataHelper: UserDataHelper,
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .padding(16.dp),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.Start
    ) {
        HeaderView(
            modifier = GlanceModifier
        )
        DataView(
            userDataHelper = userDataHelper,
        )
    }
}

@Composable
private fun HeaderView(
    modifier: GlanceModifier = GlanceModifier
) {
    Row(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_app_icon_transparent_bg),
            contentDescription = "app icon",
            modifier = GlanceModifier
                .size(40.dp)
                .padding(end = 8.dp)
        )
        val context = LocalContext.current
        val label = context.getString(com.shahzadian.baseui.R.string.app_name)
        val textStyle = AppTypography.bodyMedium
        Text(
            text = label,
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onSecondary),
                fontSize = textStyle.fontSize,
                fontWeight = FontWeight.Medium,
            )
        )
    }
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
        val textStyle = AppTypography.headlineMedium
        Text(
            text = noCigDaysText,
            style = TextStyle(
                color = ColorProvider(MaterialTheme.colorScheme.onPrimary),
                fontSize = textStyle.fontSize,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}

@AndroidEntryPoint
class FreeNicGlanceWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var userDataHelperFactory: UserDataHelperFactory

    @Inject
    lateinit var getUserDataUseCase: GetUserDataUseCase

    override val glanceAppWidget: GlanceAppWidget by lazyUnsafe {
        MainAppWidget(
            userDataHelperFactory = userDataHelperFactory,
            getUserDataUseCase = getUserDataUseCase,
        )
    }
}
