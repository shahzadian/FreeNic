package com.shahzadian.recoverycalculator.domain

interface HealthRecoveryCalculator {

    fun calculateOxygenCoNormalizationPercent(): Int

    fun calculateOxygenCoNormalizationDateMillisForPercents(percents: Int): Long

    fun calculateCirculationHeartRecoveryPercent(): Int

    fun calculateCirculationHeartRecoveryDateMillisForPercents(percents: Int): Long

    fun calculateLungSelfCleaningPercent(): Int

    fun calculateLungSelfCleaningDateMillisForPercents(percents: Int): Long

    fun calculateLongTermRiskReductionPercent(): Int

    fun calculateLongTermRiskReductionDateMillisForPercents(percents: Int): Long

    fun calculateEnergyLevelPercent(): Int

    fun calculateEnergyLevelDateMillisForPercents(percents: Int): Long

    fun calculateBreathingComfortPercent(): Int

    fun calculateBreathingComfortDateMillisForPercents(percents: Int): Long

    fun calculatePhysicalEndurancePercent(): Int

    fun calculatePhysicalEnduranceDateMillisForPercents(percents: Int): Long

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
