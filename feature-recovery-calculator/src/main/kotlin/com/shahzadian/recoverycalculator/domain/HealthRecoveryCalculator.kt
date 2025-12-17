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
}
