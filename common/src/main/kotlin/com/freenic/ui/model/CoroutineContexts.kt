package com.freenic.ui.model

import kotlinx.coroutines.CoroutineDispatcher

class CoroutineContexts(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher,
)
