package com.shahzadian.recoverycalculator.domain

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.model.UserData
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.roundToInt
import kotlin.math.roundToLong

//TODO refactor class and algorithms
internal class HealthRecoveryCalculatorImpl(
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

    override fun calculateOxygenCoNormalizationDateMillisForPercents(percents: Int): Long {
        val points = listOf(
            0 to 0.0,
            1 to 75.0,
            2 to 92.0,
            3 to 100.0
        )

        val clampedTarget = percents.coerceIn(0, 100)

        for (i in 0 until points.size - 1) {
            val (day1, p1) = points[i]
            val (day2, p2) = points[i + 1]

            if (clampedTarget.toDouble() in p1..p2) {
                val ratio = (clampedTarget - p1) / (p2 - p1)
                val days = day1 + ratio * (day2 - day1)

                return userData.quitData.quitDateMillis + (days * MILLIS_IN_DAY).toLong()
            }
        }

        // если 100% — возвращаем 3-й день
        return userData.quitData.quitDateMillis + 3L * MILLIS_IN_DAY
    }

    override fun calculateCirculationHeartRecoveryPercent(): Int {
        val base = piecewisePercent(
            skippedDaysCount, listOf(
                0 to 0.0,
                7 to 10.0,
                30 to 22.0,
                90 to 38.0,
                365 to 65.0,
                1825 to 90.0,
                3650 to 100.0
            )
        )
        return ageAdjusted(base, age, sensitivity = 0.35)
    }

    override fun calculateCirculationHeartRecoveryDateMillisForPercents(percents: Int): Long {
        val points = listOf(
            0 to 0.0,
            7 to 10.0,
            30 to 22.0,
            90 to 38.0,
            365 to 65.0,
            1825 to 90.0,
            3650 to 100.0
        )

        val clampedTarget = percents.coerceIn(0, 100)

        for (i in 0 until points.size - 1) {
            val (day1, p1) = points[i]
            val (day2, p2) = points[i + 1]

            if (clampedTarget.toDouble() in p1..p2) {
                val ratio = (clampedTarget - p1) / (p2 - p1)
                val days = day1 + ratio * (day2 - day1)

                return userData.quitData.quitDateMillis + (days * MILLIS_IN_DAY).toLong()
            }
        }

        // Если 100% — возвращаем 3650-й день (10 лет)
        return userData.quitData.quitDateMillis + 3650L * MILLIS_IN_DAY
    }

    override fun calculateLungSelfCleaningPercent(): Int {
        val base = piecewisePercent(
            skippedDaysCount, listOf(
                0 to 0.0,
                14 to 8.0,
                60 to 25.0,
                180 to 55.0,
                270 to 70.0,
                365 to 82.0,
                730 to 92.0,
                1825 to 100.0
            )
        )
        return ageAdjusted(base, age, sensitivity = 0.45)
    }

    override fun calculateLungSelfCleaningDateMillisForPercents(percents: Int): Long {
        val points = listOf(
            0 to 0.0,
            14 to 8.0,
            60 to 25.0,
            180 to 55.0,
            270 to 70.0,
            365 to 82.0,
            730 to 92.0,
            1825 to 100.0
        )

        val clampedTarget = percents.coerceIn(0, 100)

        for (i in 0 until points.size - 1) {
            val (day1, p1) = points[i]
            val (day2, p2) = points[i + 1]

            if (clampedTarget.toDouble() in p1..p2) {
                val ratio = (clampedTarget - p1) / (p2 - p1)
                val days = day1 + ratio * (day2 - day1)

                return userData.quitData.quitDateMillis + (days * MILLIS_IN_DAY).toLong()
            }
        }

        // 100% — 1825 дней (~5 лет)
        return userData.quitData.quitDateMillis + 1825L * MILLIS_IN_DAY
    }

    override fun calculateLongTermRiskReductionPercent(): Int {
        val base = piecewisePercent(
            skippedDaysCount, listOf(
                0 to 0.0,
                365 to 12.0,
                1825 to 35.0,
                3650 to 60.0,
                5475 to 80.0,  // 15 years
                7300 to 100.0  // 20 years
            )
        )
        return ageAdjusted(base, age, sensitivity = 0.25)
    }

    override fun calculateLongTermRiskReductionDateMillisForPercents(percents: Int): Long {
        val points = listOf(
            0 to 0.0,
            365 to 12.0,
            1825 to 35.0,
            3650 to 60.0,
            5475 to 80.0,  // 15 years
            7300 to 100.0  // 20 years
        )

        val clampedTarget = percents.coerceIn(0, 100)

        for (i in 0 until points.size - 1) {
            val (day1, p1) = points[i]
            val (day2, p2) = points[i + 1]

            if (clampedTarget.toDouble() in p1..p2) {
                val ratio = (clampedTarget - p1) / (p2 - p1)
                val days = day1 + ratio * (day2 - day1)

                return userData.quitData.quitDateMillis + (days * MILLIS_IN_DAY).toLong()
            }
        }

        // 100% — 7300 дней (~20 лет)
        return userData.quitData.quitDateMillis + 7300L * MILLIS_IN_DAY
    }

    override fun calculateEnergyLevelPercent(): Int {
        val base = piecewisePercent(
            skippedDaysCount, listOf(
                0 to 0.0,
                3 to 12.0,
                14 to 35.0,
                30 to 55.0,
                90 to 78.0,
                180 to 90.0,
                365 to 100.0
            )
        )
        return ageAdjusted(base, age, sensitivity = 0.30)
    }

    override fun calculateEnergyLevelDateMillisForPercents(percents: Int): Long {
        val points = listOf(
            0 to 0.0,
            3 to 12.0,
            14 to 35.0,
            30 to 55.0,
            90 to 78.0,
            180 to 90.0,
            365 to 100.0
        )

        val clampedTarget = percents.coerceIn(0, 100)
        for (i in 0 until points.size - 1) {
            val (day1, p1) = points[i]
            val (day2, p2) = points[i + 1]

            if (clampedTarget.toDouble() in p1..p2) {
                val ratio = (clampedTarget - p1) / (p2 - p1)
                val days = day1 + ratio * (day2 - day1)

                return userData.quitData.quitDateMillis + (days * MILLIS_IN_DAY).toLong()
            }
        }

        // если 100% — возвращаем последний день
        return userData.quitData.quitDateMillis + 365L * MILLIS_IN_DAY
    }

    override fun calculateBreathingComfortPercent(): Int {
        val base = piecewisePercent(
            skippedDaysCount, listOf(
                0 to 0.0,
                7 to 10.0,
                30 to 30.0,
                90 to 55.0,
                180 to 75.0,
                365 to 92.0,
                730 to 100.0
            )
        )
        return ageAdjusted(base, age, sensitivity = 0.40)
    }

    override fun calculateBreathingComfortDateMillisForPercents(percents: Int): Long {
        val points = listOf(
            0 to 0.0,
            7 to 10.0,
            30 to 30.0,
            90 to 55.0,
            180 to 75.0,
            365 to 92.0,
            730 to 100.0
        )

        val clampedTarget = percents.coerceIn(0, 100)

        for (i in 0 until points.size - 1) {
            val (day1, p1) = points[i]
            val (day2, p2) = points[i + 1]

            if (clampedTarget.toDouble() in p1..p2) {
                val ratio = (clampedTarget - p1) / (p2 - p1)
                val days = day1 + ratio * (day2 - day1)

                return userData.quitData.quitDateMillis + (days * MILLIS_IN_DAY).toLong()
            }
        }

        // 100% — 730 дней (~2 года)
        return userData.quitData.quitDateMillis + 730L * MILLIS_IN_DAY
    }

    override fun calculatePhysicalEndurancePercent(): Int {
        val base = piecewisePercent(
            skippedDaysCount, listOf(
                0 to 0.0,
                14 to 15.0,
                30 to 32.0,
                90 to 60.0,
                180 to 78.0,
                365 to 92.0,
                730 to 100.0
            )
        )
        return ageAdjusted(base, age, sensitivity = 0.45)
    }

    override fun calculatePhysicalEnduranceDateMillisForPercents(percents: Int): Long {
        val points = listOf(
            0 to 0.0,
            14 to 15.0,
            30 to 32.0,
            90 to 60.0,
            180 to 78.0,
            365 to 92.0,
            730 to 100.0
        )

        val clampedTarget = percents.coerceIn(0, 100)

        for (i in 0 until points.size - 1) {
            val (day1, p1) = points[i]
            val (day2, p2) = points[i + 1]

            if (clampedTarget.toDouble() in p1..p2) {
                val ratio = (clampedTarget - p1) / (p2 - p1)
                val days = day1 + ratio * (day2 - day1)

                return userData.quitData.quitDateMillis + (days * MILLIS_IN_DAY).toLong()
            }
        }

        // 100% — 730 дней (~2 года)
        return userData.quitData.quitDateMillis + 730L * MILLIS_IN_DAY
    }

    /**
     * Estimated minutes of life "regained" since quitting.
     * Uses a simple heuristic: 1 cigarette ~ 11 minutes (population-level estimate).
     */
    override fun calculateMinutesRegained(): Long {
        val cigsAvoided = skippedDaysCount.toLong() * cigsPerDay.coerceAtLeast(0).toLong()
        return cigsAvoided * 11L
    }

    override fun calculateBloodPressureStabilizationPercent(): Int {
        val daysSinceQuitClamped = getDaysSinceQuit().coerceAtLeast(0)
        // Ensure we never use negative time (just in case input millisSinceQuit is negative).

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Convert cigarettes/day into a multiplier:
        // heavier smoking -> slightly slower stabilization progress, lighter smoking -> slightly faster.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Convert age into a multiplier:
        // younger age -> slightly faster stabilization progress, older age -> slightly slower.

        val effectiveRecoveryDays = daysSinceQuitClamped * smokingLoadFactor * ageRecoveryFactor
        // "Effective days" = real days adjusted by smoking intensity and age.
        // This makes the curve mildly personalized without going extreme.

        val rawProgress01 = exponentialApproachToOne(effectiveRecoveryDays, timeConstantDays = 7.0)
        // Compute progress in 0..1 using an exponential curve:
        // p = 1 - exp(-t / tau), where tau=7 days (fast improvement in first weeks).

        val progressCappedForFirstDay01 = when {
            effectiveRecoveryDays < 0.25 -> 0.02
            // First 6 hours: show tiny but non-zero progress (UX-friendly and realistic).

            effectiveRecoveryDays < 1.0 -> 0.07
            // First day: do NOT allow the model to jump too high.
            // Stabilization is not "done" within 24 hours.

            else -> rawProgress01
            // After day 1: use the curve normally.
        }
        // Apply an early-time guardrail so users don't see 90–99% on day 0/1 due to bugs or clamping.

        val progressPercent = (progressCappedForFirstDay01.coerceIn(0.0, 1.0) * 100.0).roundToInt()
        // Convert 0..1 progress to integer percent 0..100.

        return progressPercent.coerceIn(0, 100)
        // Safety clamp: percent must always stay inside 0..100.
    }

    override fun calculateBloodPressureDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Ensure target percent is within a valid and meaningful range.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent (e.g. 85) to progress value (0.85).

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Get smoking-related recovery speed multiplier.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Get age-related recovery speed multiplier.

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier used in the main recovery algorithm.

        val timeConstantDays = 7.0
        // Time constant for blood pressure recovery (must match main algorithm).

        // Invert the exponential recovery function:
        // progress = 1 - exp(-effectiveDays / timeConstantDays)
        // effectiveDays = -timeConstantDays * ln(1 - progress)
        val effectiveDaysRequired =
            -timeConstantDays * ln(1.0 - targetProgress01)
        // Calculate how many "effective days" are required to reach target progress.

        val realDaysRequired =
            effectiveDaysRequired / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real calendar days.

        val millisPerDay = 86_400_000.0
        // Number of milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert days to milliseconds.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
    }

    override fun calculateImmuneRecoveryPercent(): Int {
        val daysSinceQuitNonNegative = getDaysSinceQuit().coerceAtLeast(0)
        // Ensure time is not negative in case millisSinceQuit is negative.

        val cigarettesPerDayClamped = userData.cigarettesData.cigarettesPerDay.coerceIn(0, 60)
        // Clamp cigarettes/day to a reasonable range to avoid extreme personalization.

        val smokingLoadFactor = calculateSmokingLoadFactor(cigarettesPerDayClamped)
        // Map smoking intensity to a multiplier that gently affects recovery speed.

        val ageClamped = userData.personalData.age.coerceIn(18, 90)
        // Clamp age to a reasonable adult range.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(ageClamped)
        // Map age to a multiplier that gently affects recovery speed.

        val effectiveRecoveryDays = daysSinceQuitNonNegative * smokingLoadFactor * ageRecoveryFactor
        // Compute "effective days" which represent personalized recovery time.

        val effectiveDaysLimitedToModelWindow = effectiveRecoveryDays.coerceIn(0.0, 180.0)
        // Limit to 0..180 days because we model immune recovery over ~6 months for UI.

        val midpointDaysForHalfRecovery = 45.0
        // Midpoint: around this many effective days we expect the curve to reach ~50%.

        val logisticSteepness = 0.06
        // Steepness controls how quickly the curve rises around the midpoint.

        val immuneRecoveryProgress01 =
            1.0 / (1.0 + exp(-logisticSteepness * (effectiveDaysLimitedToModelWindow - midpointDaysForHalfRecovery)))
        // Logistic curve (0..1) to model gradual immune recovery over weeks/months.

        val progressGuardedForVeryEarlyDays01 = when {
            effectiveDaysLimitedToModelWindow < 1.0 -> 0.02
            // Day 0..1: show minimal progress (immune system does not "recover" instantly).

            effectiveDaysLimitedToModelWindow < 7.0 -> immuneRecoveryProgress01.coerceAtMost(0.15)
            // First week: cap progress so it doesn't jump unrealistically high.

            else -> immuneRecoveryProgress01
            // After week 1: use the curve normally.
        }
        // Apply small UX/realism guardrails so early values look believable.

        val immuneRecoveryPercent =
            (progressGuardedForVeryEarlyDays01.coerceIn(0.0, 1.0) * 100.0).roundToInt()
        // Convert 0..1 progress to integer percent.

        return immuneRecoveryPercent.coerceIn(0, 100)
        // Ensure the output always stays in 0..100.
    }

    override fun calculateImmuneRecoveryDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Clamp to 1..99 to avoid infinite values near 0% and 100%.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent to 0..1 progress.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity multiplier (heavier smoking -> slower progress).

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age multiplier (older -> slightly slower progress).

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier for converting real days to effective days.

        val midpointDays = 45.0
        // Logistic midpoint: around this many effective days progress is ~50%.

        val logisticSteepness = 0.06
        // Logistic steepness: controls how fast the curve rises.

        // Invert logistic:
        // progress = 1 / (1 + exp(-k*(x - mid)))
        // 1/progress - 1 = exp(-k*(x - mid))
        // ln(1/progress - 1) = -k*(x - mid)
        // x = mid - (1/k)*ln(1/progress - 1)
        val effectiveDaysRequired =
            midpointDays - (1.0 / logisticSteepness) * ln((1.0 / targetProgress01) - 1.0)
        // Compute how many effective days are required to reach the target progress.

        val effectiveDaysRequiredNonNegative = effectiveDaysRequired.coerceAtLeast(0.0)
        // Ensure we never go negative (very low targets could mathematically imply < 0 days).

        val realDaysRequired =
            effectiveDaysRequiredNonNegative / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real calendar days.

        val millisPerDay = 86_400_000.0
        // Milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert required days to milliseconds.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
        // Return the absolute date in epoch millis.
    }

    override fun calculateStressRegulationPercent(): Int {
        val daysSinceQuit = getDaysSinceQuit()
        // Convert milliseconds since quit into days using your existing helper.

        val daysSinceQuitNonNegative = daysSinceQuit.coerceAtLeast(0)
        // Ensure days since quit is not negative (defensive guard).

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Get recovery speed multiplier based on cigarettes/day (heavier smoking -> slower progress).

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Get recovery speed multiplier based on age (younger -> slightly faster, older -> slightly slower).

        val effectiveRecoveryDays = daysSinceQuitNonNegative * smokingLoadFactor * ageRecoveryFactor
        // Compute "effective" recovery time in days after applying personalization multipliers.

        val effectiveDaysLimitedToModelWindow = effectiveRecoveryDays.coerceIn(0.0, 90.0)
        // Limit to the 0..90 days window because we model stress regulation over ~3 months for UI.

        val midpointDaysForHalfImprovement = 28.0
        // Set the logistic midpoint: around 4 weeks the model reaches ~50% improvement.

        val logisticSteepness = 0.08
        // Set curve steepness: controls how quickly progress increases around the midpoint.

        val stressRegulationProgress01 =
            1.0 / (1.0 + exp(-logisticSteepness * (effectiveDaysLimitedToModelWindow - midpointDaysForHalfImprovement)))
        // Calculate logistic progress (0..1) for stress regulation improvement.

        val progressGuardedForEarlyWithdrawal01 = when {
            effectiveDaysLimitedToModelWindow < 1.0 -> 0.02
            // First day: keep progress minimal because stress can feel worse initially.

            effectiveDaysLimitedToModelWindow < 7.0 -> stressRegulationProgress01.coerceAtMost(0.12)
            // First week: cap progress; adaptation and mood swings are common.

            effectiveDaysLimitedToModelWindow < 14.0 -> stressRegulationProgress01.coerceAtMost(0.25)
            // Second week: still cap to keep early numbers believable.

            else -> stressRegulationProgress01
            // After two weeks: use the logistic curve normally.
        }
        // Apply guardrails so the user never sees unrealistically high early percentages.

        val stressRegulationPercent =
            (progressGuardedForEarlyWithdrawal01.coerceIn(0.0, 1.0) * 100.0).roundToInt()
        // Convert 0..1 progress to an Int percent (0..100).

        return stressRegulationPercent.coerceIn(0, 100)
        // Final safety clamp to keep output in valid percent range.
    }

    override fun calculateStressRegulationDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Clamp to 1..99 to avoid infinities near 0% and 100%.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent to 0..1 progress.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity multiplier (affects adaptation speed).

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age multiplier (gentle effect).

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier used in the percent algorithm.

        val midpointDays = 28.0
        // Must match your stress regulation percent model (50% around ~4 weeks).

        val logisticSteepness = 0.08
        // Must match your stress regulation percent model.

        val effectiveDaysRequired =
            midpointDays - (1.0 / logisticSteepness) * ln((1.0 / targetProgress01) - 1.0)
        // Invert logistic curve to compute required effective days.

        val effectiveDaysRequiredNonNegative = effectiveDaysRequired.coerceAtLeast(0.0)
        // Ensure non-negative (very low targets could mathematically imply <0 days).

        val realDaysRequired =
            effectiveDaysRequiredNonNegative / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real calendar days.

        val millisPerDay = 86_400_000.0
        // Milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert required days to milliseconds since quit.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
        // Return absolute target date in epoch millis.
    }

    override fun calculateSleepQualityPercent(): Int {
        val daysSinceQuit = getDaysSinceQuit()
        // Convert milliseconds since quit to days using your existing helper.

        val daysSinceQuitNonNegative = daysSinceQuit.coerceAtLeast(0)
        // Ensure we never use negative time (defensive guard).

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Get speed multiplier based on prior cigarettes/day.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Get speed multiplier based on age.

        val effectiveRecoveryDays = daysSinceQuitNonNegative * smokingLoadFactor * ageRecoveryFactor
        // Compute personalized "effective days" of recovery.

        val modelWindowDays = 45.0
        // Define the window where sleep improvements are most noticeable for the UI curve.

        val effectiveDaysLimitedToModelWindow = effectiveRecoveryDays.coerceIn(0.0, modelWindowDays)
        // Limit effective days to the model window to avoid unnecessary extremes.

        val timeConstantDays = 10.0
        // Time constant for exponential approach: faster changes in the first weeks, then slows down.

        val rawProgress01 = exponentialApproachToOne(
            timeDays = effectiveDaysLimitedToModelWindow,
            timeConstantDays = timeConstantDays
        )
        // Compute sleep improvement progress (0..1) using your existing exponential helper.

        val progressGuardedForVeryEarlyDays01 = when {
            effectiveDaysLimitedToModelWindow < 1.0 -> 0.03
            // First day: keep progress minimal (sleep may be unstable during acute withdrawal).

            effectiveDaysLimitedToModelWindow < 3.0 -> rawProgress01.coerceAtMost(0.15)
            // First 3 days: cap progress to avoid unrealistic jumps.

            else -> rawProgress01
            // After a few days: use the curve normally.
        }
        // Apply small guardrails so the first days look believable.

        val sleepQualityPercent =
            (progressGuardedForVeryEarlyDays01.coerceIn(0.0, 1.0) * 100.0).roundToInt()
        // Convert progress 0..1 into percent 0..100.

        return sleepQualityPercent.coerceIn(0, 100)
        // Final safety clamp to ensure valid percent output.
    }

    override fun calculateSleepQualityDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Clamp to 1..99 because 100% is unreachable for an exponential curve.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent to 0..1 progress.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity multiplier.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age multiplier.

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier used in your percent algorithm.

        val timeConstantDays = 10.0
        // Must match your sleep percent algorithm (the tau you used there).

        val effectiveDaysRequired =
            -timeConstantDays * ln(1.0 - targetProgress01)
        // Invert the exponential curve to get required effective days for target progress.

        val realDaysRequired =
            effectiveDaysRequired / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real days using the same multipliers.

        val millisPerDay = 86_400_000.0
        // Milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert required days to milliseconds since quit.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
        // Return absolute target date in epoch millis.
    }

    override fun calculateTasteAndSmellRecoveryPercent(): Int {
        val daysSinceQuit = getDaysSinceQuit()
        // Convert milliseconds since quit to days using your helper.

        val daysSinceQuitNonNegative = daysSinceQuit.coerceAtLeast(0)
        // Ensure time is not negative (defensive guard).

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Get speed multiplier based on prior cigarettes/day (heavier smoking -> slightly slower recovery).

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Get speed multiplier based on age (younger -> slightly faster recovery).

        val effectiveRecoveryDays = daysSinceQuitNonNegative * smokingLoadFactor * ageRecoveryFactor
        // Compute personalized "effective days" for taste/smell recovery.

        val modelWindowDays = 14.0
        // Define the main UI window: taste and smell often improve within ~1–2 weeks.

        val effectiveDaysLimitedToModelWindow = effectiveRecoveryDays.coerceIn(0.0, modelWindowDays)
        // Limit effective days to 0..14 to keep the curve focused on the expected timeframe.

        val timeConstantDays = 3.0
        // Set exponential time constant: fast improvement early, then gradually slows.

        val rawProgress01 = exponentialApproachToOne(
            timeDays = effectiveDaysLimitedToModelWindow,
            timeConstantDays = timeConstantDays
        )
        // Calculate raw recovery progress in 0..1.

        val progressGuardedForFirstDay01 = when {
            effectiveDaysLimitedToModelWindow < 1.0 -> rawProgress01.coerceAtMost(0.10)
            // First day: cap progress so it doesn't look like full recovery instantly.

            else -> rawProgress01
            // After day 1: use the curve normally.
        }
        // Apply a small realism guardrail for the very early period.

        val tasteAndSmellPercent =
            (progressGuardedForFirstDay01.coerceIn(0.0, 1.0) * 100.0).roundToInt()
        // Convert 0..1 progress to percent 0..100.

        return tasteAndSmellPercent.coerceIn(0, 100)
        // Final clamp to keep output within valid percent range.
    }

    override fun calculateTasteAndSmellRecoveryDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Clamp to 1..99 because exponential curves never reach 100% exactly.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent to 0..1 progress.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity multiplier.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age multiplier.

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier used in the percent algorithm.

        val timeConstantDays = 3.0
        // Must match your taste & smell percent algorithm.

        val effectiveDaysRequired =
            -timeConstantDays * ln(1.0 - targetProgress01)
        // Invert the exponential model to find required effective days.

        val realDaysRequired =
            effectiveDaysRequired / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real days.

        val millisPerDay = 86_400_000.0
        // Milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert required days to milliseconds since quit.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
        // Return absolute target date in epoch millis.
    }

    override fun calculateSkinImprovementPercent(): Int {
        val daysSinceQuit = getDaysSinceQuit()
        // Convert milliseconds since quit to days using your helper.

        val daysSinceQuitNonNegative = daysSinceQuit.coerceAtLeast(0)
        // Ensure we never use negative time.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Get recovery speed multiplier based on smoking intensity.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Get recovery speed multiplier based on age.

        val effectiveRecoveryDays = daysSinceQuitNonNegative * smokingLoadFactor * ageRecoveryFactor
        // Compute personalized "effective days" for skin recovery.

        val modelWindowDays = 180.0
        // Skin improvements are gradual; we model the main visible change over ~6 months.

        val effectiveDaysLimitedToModelWindow = effectiveRecoveryDays.coerceIn(0.0, modelWindowDays)
        // Limit effective days to the model window for stable UI behavior.

        val timeConstantDays = 60.0
        // Exponential time constant: slow, long-term curve (about ~63% by ~60 days, ~95% by ~180 days).

        val rawProgress01 = exponentialApproachToOne(
            timeDays = effectiveDaysLimitedToModelWindow,
            timeConstantDays = timeConstantDays
        )
        // Calculate raw skin improvement progress (0..1).

        val percentNotMoreThanNinetyNine = (rawProgress01.coerceIn(0.0, 0.99) * 100.0).roundToInt()
        // Convert to percent and cap at 99% by design (avoid showing 100%).

        return percentNotMoreThanNinetyNine.coerceIn(0, 99)
        // Final safety clamp to keep output in 0..99.
    }

    override fun calculateSkinImprovementDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Clamp to 1..99 because exponential curves never reach 100% exactly.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent to 0..1 progress.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity multiplier (heavier smoking -> slower improvement).

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age multiplier (older -> slower improvement).

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier used in the percent algorithm.

        val timeConstantDays = 60.0
        // Must match your skin improvement percent algorithm.

        val effectiveDaysRequired =
            -timeConstantDays * ln(1.0 - targetProgress01)
        // Invert exponential model to get required effective days for target progress.

        val realDaysRequired =
            effectiveDaysRequired / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real days.

        val millisPerDay = 86_400_000.0
        // Milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert required days to milliseconds since quit.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
        // Return absolute target date in epoch millis.
    }

    override fun calculateStrokeRiskReductionPercent(): Int {
        val daysSinceQuit = getDaysSinceQuit()
        // Convert milliseconds since quit to days using your helper.

        val daysSinceQuitNonNegative = daysSinceQuit.coerceAtLeast(0)
        // Ensure we never use negative time.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Get speed multiplier based on prior smoking intensity.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Get speed multiplier based on age.

        val effectiveRecoveryDays = daysSinceQuitNonNegative * smokingLoadFactor * ageRecoveryFactor
        // Compute personalized "effective days" (gentle personalization for long-term risk).

        val modelWindowYears = 5.0
        // Define the UI model window: stroke risk reduction is meaningful over multiple years.

        val daysInYear = 365.25
        // Use average year length to convert years to days.

        val modelWindowDays = modelWindowYears * daysInYear
        // Convert the model window from years to days.

        val effectiveDaysLimitedToModelWindow = effectiveRecoveryDays.coerceIn(0.0, modelWindowDays)
        // Limit effective days so the curve stays stable and doesn't overshoot.

        val timeConstantYears = 1.8
        // Choose time constant in years: faster gains in early years, then slower improvements.

        val timeConstantDays = timeConstantYears * daysInYear
        // Convert time constant from years to days for the exponential helper.

        val rawProgress01 = exponentialApproachToOne(
            timeDays = effectiveDaysLimitedToModelWindow,
            timeConstantDays = timeConstantDays
        )
        // Compute risk-reduction progress (0..1) over the multi-year horizon.

        val percentNotMoreThanNinetyNine = (rawProgress01.coerceIn(0.0, 0.99) * 100.0).roundToInt()
        // Convert to percent and cap at 99% by design.

        return percentNotMoreThanNinetyNine.coerceIn(0, 99)
        // Final clamp to keep output within 0..99.
    }

    override fun calculateStrokeRiskReductionDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Clamp to 1..99 because exponential curves never reach 100% exactly.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent to 0..1 progress.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity multiplier.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age multiplier.

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier used in the percent algorithm.

        val daysInYear = 365.25
        // Average year length used for conversion.

        val timeConstantYears = 1.8
        // Must match your stroke risk reduction percent algorithm.

        val timeConstantDays = timeConstantYears * daysInYear
        // Convert time constant from years to days for the exponential inversion.

        val effectiveDaysRequired =
            -timeConstantDays * ln(1.0 - targetProgress01)
        // Invert exponential model to compute required effective days.

        val realDaysRequired =
            effectiveDaysRequired / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real calendar days.

        val millisPerDay = 86_400_000.0
        // Milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert required days to milliseconds since quit.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
        // Return absolute target date in epoch millis.
    }

    override fun calculateCancerRiskReductionPercent(): Int {
        val daysSinceQuit = getDaysSinceQuit()
        // Convert milliseconds since quit into days (must be duration, not timestamp).

        val daysSinceQuitNonNegative = daysSinceQuit.coerceAtLeast(0)
        // Defensive guard: never allow negative time.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity has strong impact on cancer risk, so we use this factor directly.

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age slightly affects how fast long-term risk reduction accumulates.

        val effectiveRecoveryDays =
            daysSinceQuitNonNegative * smokingLoadFactor * ageRecoveryFactor
        // Compute personalized "effective days" for long-term cancer risk reduction.

        val modelWindowYears = 10.0
        // Cancer risk reduction is a very long-term process (many years).

        val daysInYear = 365.25
        // Average year length for conversion.

        val modelWindowDays = modelWindowYears * daysInYear
        // Convert model window to days.

        val effectiveDaysLimitedToModelWindow =
            effectiveRecoveryDays.coerceIn(0.0, modelWindowDays)
        // Limit effective days to the model window to keep the curve stable.

        val timeConstantYears = 3.5
        // Time constant in years: reflects slow, cumulative reduction of cancer risk.

        val timeConstantDays = timeConstantYears * daysInYear
        // Convert time constant to days for the exponential helper.

        val rawProgress01 = exponentialApproachToOne(
            timeDays = effectiveDaysLimitedToModelWindow,
            timeConstantDays = timeConstantDays
        )
        // Exponential curve modeling gradual long-term cancer risk reduction (0..1).

        val percentNotMoreThanNinetyNine =
            (rawProgress01.coerceIn(0.0, 0.99) * 100.0).roundToInt()
        // Convert to percent and cap at 99% by design (avoid “fully safe” illusion).

        return percentNotMoreThanNinetyNine.coerceIn(0, 99)
        // Final safety clamp.
    }

    override fun calculateCancerRiskReductionDateMillisForPercents(percents: Int): Long {
        val targetPercentClamped = percents.coerceIn(1, 99)
        // Clamp to 1..99 because exponential curves never reach 100% exactly.

        val targetProgress01 = targetPercentClamped / 100.0
        // Convert percent to 0..1 progress.

        val smokingLoadFactor = calculateSmokingLoadFactor(userData.cigarettesData.cigarettesPerDay)
        // Smoking intensity multiplier (important for cancer-related progress speed).

        val ageRecoveryFactor = calculateAgeRecoveryFactor(userData.personalData.age)
        // Age multiplier (gentle effect).

        val effectiveDaysMultiplier = smokingLoadFactor * ageRecoveryFactor
        // Combined multiplier used in the percent algorithm.

        val daysInYear = 365.25
        // Average year length used for conversion.

        val timeConstantYears = 3.5
        // Must match your cancer risk reduction percent algorithm.

        val timeConstantDays = timeConstantYears * daysInYear
        // Convert time constant from years to days.

        val effectiveDaysRequired =
            -timeConstantDays * ln(1.0 - targetProgress01)
        // Invert exponential model to compute required effective days.

        val realDaysRequired =
            effectiveDaysRequired / effectiveDaysMultiplier.coerceAtLeast(0.0001)
        // Convert effective days back to real calendar days.

        val millisPerDay = 86_400_000.0
        // Milliseconds in one day.

        val millisFromQuitToTarget =
            (realDaysRequired * millisPerDay).roundToLong()
        // Convert required days to milliseconds since quit.

        return userData.quitData.quitDateMillis + millisFromQuitToTarget
        // Return absolute target date in epoch millis.
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

    /** Maps cigarettes/day to a gentle multiplier for recovery speed. */
    private fun calculateSmokingLoadFactor(cigarettesPerDayBeforeQuit: Int): Double {
        val cigarettes = cigarettesPerDayBeforeQuit.coerceIn(0, 60)
        // Clamp input to a reasonable range to avoid extreme multipliers.

        return when {
            cigarettes <= 5 -> 1.10
            // Very light smoking: slightly faster recovery.

            cigarettes <= 10 -> 1.05
            // Light smoking: a bit faster recovery.

            cigarettes <= 20 -> 1.00
            // Typical pack/day or less: baseline.

            cigarettes <= 30 -> 0.95
            // Heavier smoking: slightly slower recovery.

            cigarettes <= 40 -> 0.90
            // Very heavy smoking: slower recovery.

            else -> 0.85
            // Extremely heavy smoking: slowest allowed recovery.
        }
    }

    /** Maps age to a gentle multiplier for recovery speed. */
    private fun calculateAgeRecoveryFactor(ageYears: Int): Double {
        val age = ageYears.coerceIn(18, 90)
        // Clamp age to a reasonable adult range to avoid odd inputs.

        return when {
            age <= 30 -> 1.08
            // Younger: slightly faster stabilization.

            age <= 40 -> 1.04
            // Early middle age: a bit faster.

            age <= 55 -> 1.00
            // Baseline.

            age <= 70 -> 0.94
            // Older: slightly slower.

            else -> 0.88
            // Elderly: slowest allowed.
        }
    }

    /**
     * Exponential curve that approaches 1.0 over time:
     * progress = 1 - exp(-time / timeConstant)
     */
    private fun exponentialApproachToOne(timeDays: Double, timeConstantDays: Double): Double {
        val safeTimeDays = timeDays.coerceAtLeast(0.0)
        // Make sure time is non-negative.

        val safeTimeConstant = timeConstantDays.coerceAtLeast(0.0001)
        // Prevent division by zero.

        return 1.0 - exp(-safeTimeDays / safeTimeConstant)
        // Core exponential approach-to-one formula.
    }

    private fun getDaysSinceQuit(): Long {
        return (System.currentTimeMillis() - userData.quitData.quitDateMillis) / 86_400_000L
    }

    private companion object {
        const val MILLIS_IN_DAY = 24L * 60 * 60 * 1000
    }
}