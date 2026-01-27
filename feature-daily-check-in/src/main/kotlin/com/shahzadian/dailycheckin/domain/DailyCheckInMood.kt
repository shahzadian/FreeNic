package com.shahzadian.dailycheckin.domain

import com.freenic.ui.wrapper.ImageWrapper
import com.shahzadian.dailycheckin.R

enum class DailyCheckInMood {
    HARD {
        override fun getIcon(): ImageWrapper {
            return ImageWrapper.FromRes(R.drawable.ic_daily_check_in_hard)
        }
    },
    OKAY {
        override fun getIcon(): ImageWrapper {
            return ImageWrapper.FromRes(R.drawable.ic_daily_check_in_okay)
        }
    },
    HAPPY {
        override fun getIcon(): ImageWrapper {
            return ImageWrapper.FromRes(R.drawable.ic_daily_check_in_happy)
        }
    };

    abstract fun getIcon(): ImageWrapper
}