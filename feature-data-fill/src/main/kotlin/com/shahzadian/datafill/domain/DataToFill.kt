package com.shahzadian.datafill.domain

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo

internal class DataToFill(
    val pagesCount: Int,
    val currentPageIndex: Int,
    val info: FillableInfo,
)
