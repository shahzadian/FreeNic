package com.freenic.ui.extensions

fun <T> lazyUnsafe(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { initializer() }