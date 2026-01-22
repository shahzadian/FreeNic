package com.shahzadian.recoverycalculator.domain

interface HealthRecoveryCalculator {

    fun calculateOxygenCoNormalizationPercent(): Int

    fun calculateCirculationHeartRecoveryPercent(): Int

    fun calculateLungSelfCleaningPercent(): Int

    fun calculateLongTermRiskReductionPercent(): Int

    fun calculateEnergyLevelPercent(): Int

    fun calculateBreathingComfortPercent(): Int

    fun calculatePhysicalEndurancePercent(): Int

    fun calculateMinutesRegained(): Long

    fun calculateBloodPressureStabilizationPercent(): Int

    fun calculateBloodPressureDateMillisForPercents(percents: Int): Long

    fun calculateImmuneRecoveryPercent(): Int

    fun calculateImmuneRecoveryDateMillisForPercents(percents: Int): Long

    fun calculateStressRegulationPercent(): Int

    fun calculateStressRegulationDateMillisForPercents(percents: Int): Long

    fun calculateSleepQualityPercent(): Int

    fun calculateSleepQualityDateMillisForPercents(percents: Int): Long

    fun calculateTasteAndSmellRecoveryPercent(): Int

    fun calculateTasteAndSmellRecoveryDateMillisForPercents(percents: Int): Long

    fun calculateSkinImprovementPercent(): Int

    fun calculateSkinImprovementDateMillisForPercents(percents: Int): Long

    fun calculateStrokeRiskReductionPercent(): Int

    fun calculateStrokeRiskReductionDateMillisForPercents(percents: Int): Long

    fun calculateCancerRiskReductionPercent(): Int

    fun calculateCancerRiskReductionDateMillisForPercents(percents: Int): Long
}
