package com.shahzadian.recoverycalculator.domain

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.model.UserData
import javax.inject.Inject
import kotlin.math.roundToInt

internal class HealthRecoveryCalculatorImpl @Inject constructor(
    private val userData: UserData,
    private val userDataHelper: UserDataHelper
) : HealthRecoveryCalculator {

    private val skippedDaysCount: Int by lazyUnsafe { userDataHelper.getSkippedDaysCount() }
    private val age: Int by lazyUnsafe { userData.personalData.age }
    private val cigsPerDay: Int by lazyUnsafe { userData.cigarettesData.cigarettesPerDay }

    override fun calculateOxygenCoNormalizationPercent(): Int {
        val base = piecewisePercent(
            skippedDaysCount, listOf(
                0 to 0.0,
                1 to 75.0,
                2 to 92.0,
                3 to 100.0
            )
        )
        return ageAdjusted(base, age, sensitivity = 0.15)
    }

    override fun calculateCirculationHeartRecoveryPercent(): Int {
        val base = piecewisePercent(skippedDaysCount, listOf(
            0 to 0.0,
            7 to 10.0,
            30 to 22.0,
            90 to 38.0,
            365 to 65.0,
            1825 to 90.0,
            3650 to 100.0
        ))
        return ageAdjusted(base, age, sensitivity = 0.35)
    }

    override fun calculateLungSelfCleaningPercent(): Int {
        val base = piecewisePercent(skippedDaysCount, listOf(
            0 to 0.0,
            14 to 8.0,
            60 to 25.0,
            180 to 55.0,
            270 to 70.0,
            365 to 82.0,
            730 to 92.0,
            1825 to 100.0
        ))
        return ageAdjusted(base, age, sensitivity = 0.45)
    }

    override fun calculateLongTermRiskReductionPercent(): Int {
        val base = piecewisePercent(skippedDaysCount, listOf(
            0 to 0.0,
            365 to 12.0,
            1825 to 35.0,
            3650 to 60.0,
            5475 to 80.0,  // 15 years
            7300 to 100.0  // 20 years
        ))
        return ageAdjusted(base, age, sensitivity = 0.25)
    }

    override fun calculateEnergyLevelPercent(): Int {
        val base = piecewisePercent(skippedDaysCount, listOf(
            0 to 0.0,
            3 to 12.0,
            14 to 35.0,
            30 to 55.0,
            90 to 78.0,
            180 to 90.0,
            365 to 100.0
        ))
        return ageAdjusted(base, age, sensitivity = 0.30)
    }

    override fun calculateBreathingComfortPercent(): Int {
        val base = piecewisePercent(skippedDaysCount, listOf(
            0 to 0.0,
            7 to 10.0,
            30 to 30.0,
            90 to 55.0,
            180 to 75.0,
            365 to 92.0,
            730 to 100.0
        ))
        return ageAdjusted(base, age, sensitivity = 0.40)
    }

    override fun calculatePhysicalEndurancePercent(): Int {
        val base = piecewisePercent(skippedDaysCount, listOf(
            0 to 0.0,
            14 to 15.0,
            30 to 32.0,
            90 to 60.0,
            180 to 78.0,
            365 to 92.0,
            730 to 100.0
        ))
        return ageAdjusted(base, age, sensitivity = 0.45)
    }

    /**
     * Estimated minutes of life "regained" since quitting.
     * Uses a simple heuristic: 1 cigarette ~ 11 minutes (population-level estimate).
     */
    override fun calculateMinutesRegained(): Long {
        val cigsAvoided = skippedDaysCount.toLong() * cigsPerDay.coerceAtLeast(0).toLong()
        return cigsAvoided * 11L
    }

    private fun piecewisePercent(days: Int, milestones: List<Pair<Int, Double>>): Double {
        if (milestones.isEmpty()) return 0.0
        val sorted = milestones.sortedBy { it.first }

        if (days <= sorted.first().first) return sorted.first().second
        if (days >= sorted.last().first) return sorted.last().second

        val idx = sorted.indexOfLast { it.first <= days }.coerceAtLeast(0)
        val (d0, v0) = sorted[idx]
        val (d1, v1) = sorted[idx + 1]

        val tRaw = ((days - d0).toDouble() / (d1 - d0).toDouble()).coerceIn(0.0, 1.0)
        val t = smoothStep(tRaw)
        return (v0 + (v1 - v0) * t).coerceIn(0.0, 100.0)
    }

    private fun smoothStep(t: Double): Double = t * t * (3.0 - 2.0 * t)

    /**
     * Age adjustment:
     * - Small effect early, bigger effect as progress approaches 100.
     * - sensitivity 0..1 indicates how strong age affects this metric.
     */
    private fun ageAdjusted(basePercent: Double, age: Int, sensitivity: Double): Int {
        val a = age.coerceIn(12, 100)

        val ageFactor = when {
            a <= 25 -> 1.00
            a <= 40 -> 0.97
            a <= 55 -> 0.93
            a <= 70 -> 0.88
            else -> 0.84
        }

        val nearEnd = (basePercent / 100.0).coerceIn(0.0, 1.0)
        val effective = 1.0 - (1.0 - ageFactor) * (nearEnd * sensitivity)

        return (basePercent * effective).roundToInt().coerceIn(0, 100)
    }
}