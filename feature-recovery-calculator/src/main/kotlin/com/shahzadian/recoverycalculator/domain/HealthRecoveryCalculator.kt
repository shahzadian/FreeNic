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

    fun calculateImmuneRecoveryPercent(): Int

    fun calculateStressRegulationPercent(): Int

    fun calculateSleepQualityPercent(): Int

    fun calculateTasteAndSmellRecoveryPercent(): Int

    fun calculateSkinImprovementPercent(): Int

    fun calculateStrokeRiskReductionPercent(): Int

    fun calculateCancerRiskReductionPercent(): Int
}
