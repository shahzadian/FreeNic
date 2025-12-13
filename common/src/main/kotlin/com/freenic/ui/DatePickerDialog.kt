package com.freenic.ui

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.shahzadian.baseui.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Диалог выбора даты (Jetpack Compose, Material3).
 * Показывает диалог и по нажатию OK возвращает выбранную дату через [onDateConfirmed].
 *
 * @param onDismissRequest Колбэк закрытия диалога без выбора.
 * @param onDateConfirmed Колбэк с результатом выбора, вызывается при подтверждении.
 * @param initialDateMillis Начальная выбранная дата в миллисекундах (UTC epoch millis). Если null — ничего не выбрано.
 * @param minDateMillis Минимально допустимая дата (включительно) в миллисекундах. Если null — без ограничения снизу.
 * @param maxDateMillis Максимально допустимая дата (включительно) в миллисекундах. Если null — без ограничения сверху.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateConfirmed: (DatePickerResult) -> Unit,
    initialDateMillis: Long? = null,
    minDateMillis: Long? = null,
    maxDateMillis: Long? = null,
) {
    val selectableDates = remember(minDateMillis, maxDateMillis) {
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val minOk = minDateMillis?.let { utcTimeMillis >= it } ?: true
                val maxOk = maxDateMillis?.let { utcTimeMillis <= it } ?: true
                return minOk && maxOk
            }

            // Позволяем любые годы, ограничения делаем на уровне дат
            override fun isSelectableYear(year: Int): Boolean = true
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        selectableDates = selectableDates,
    )

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            val enabled = datePickerState.selectedDateMillis != null
            TextButton(
                enabled = enabled,
                onClick = {
                    datePickerState.selectedDateMillis?.let { selectedMillis ->
                        onDateConfirmed(
                            DatePickerResult(
                                millis = selectedMillis,
                            )
                        )
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.date_picker_dialog_ok)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(R.string.date_picker_dialog_cancel)
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                headlineContentColor = MaterialTheme.colorScheme.onPrimary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationContentColor = MaterialTheme.colorScheme.onPrimary,
                yearContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledDayContentColor = MaterialTheme.colorScheme.onSecondary,
            ),
        )
    }
}

data class DatePickerResult(
    val millis: Long,
)
