package com.freenic.ui.model

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CoroutineContexts @Inject constructor(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher,
)
