package com.shahzadian.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Базовый виджет FreeNic на Glance. Внутри оставлен отдельный слот для вашей UI-компоновки.
 */
class FreeNicGlanceWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val time = try {
            ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        } catch (_: Throwable) { "" }

        provideContent {
            Column(
                modifier = GlanceModifier.fillMaxSize(),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.Start
            ) {
                // Заголовок
                Text(text = context.getString(com.shahzadian.baseui.R.string.free_nic_widget_main_title))

                // Время (просто пример динамического контента)
                if (time.isNotEmpty()) {
                    Text(text = "Some text")
                }

                // --- НАЧАЛО ЗОНЫ ДЛЯ UI ---
                // Оставьте свой UI здесь: замените на нужные вам элементы Glance
                // Пример плейсхолдера
                Text(text = "Some placeholder")
                // --- КОНЕЦ ЗОНЫ ДЛЯ UI ---
            }
        }
    }
}

/**
 * Receiver провайдера виджета.
 */
class FreeNicGlanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = FreeNicGlanceWidget()
}
