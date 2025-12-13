package com.shahzadian.profilescreen.ui

import androidx.annotation.StringRes
import com.shahzadian.baseui.R

/**
 * Валидация пользовательских данных (уровень UI, но без Compose-логики).
 * Возвращает идентификатор строкового ресурса ошибки или null, если ошибки нет.
 */
object UserDataValidator {

    const val MIN_AGE: Int = 10
    const val MAX_AGE: Int = 120
    const val MIN_PRICE: Float = 0.1f

    /**
     * Валидация возраста в виде текста (разрешаем пустое и некорректное число как «без ошибки» во время ввода).
     * Возвращает resId текста ошибки или null.
     */
    @StringRes
    fun validateAgeText(text: String): Int? {
        val value = text.toIntOrNull() ?: return null
        return if (value < MIN_AGE) R.string.validation_age_min else null
    }

    /**
     * Валидация цены пачки, допускаем запятую и точку как разделитель.
     * Возвращает resId текста ошибки или null.
     */
    @StringRes
    fun validatePriceText(text: String): Int? {
        val normalized = text.replace(',', '.')
        val value = normalized.toFloatOrNull() ?: return null
        return if (value < MIN_PRICE) R.string.validation_price_min else null
    }
}
