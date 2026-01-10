package com.freenic.ui.extensions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun <T> lazyUnsafe(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { initializer() }

fun extractMainDisplayableDate(quitDateMillis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
    return Instant.ofEpochMilli(quitDateMillis)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}