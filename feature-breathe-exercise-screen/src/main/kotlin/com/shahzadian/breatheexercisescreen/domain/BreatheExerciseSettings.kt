package com.shahzadian.breatheexercisescreen.domain

import com.freenic.ui.wrapper.TextWrapper

class BreatheExerciseSettings(
    val inhaleSeconds: Int,
    val holdSeconds: Int,
    val exhaleSeconds: Int,
    val cyclesCount: Int,
    val helpingTexts: List<TextWrapper>,
)
