package com.shahzadian.journeycalendarscreen.ui.calendarhelper

import androidx.compose.runtime.Immutable
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.ImageWrapper
import com.shahzadian.journeycalendarscreen.R

@Immutable
internal sealed interface DayEventUiModel {

    val icon: ImageWrapper

    data object Slip : DayEventUiModel {

        override val icon: ImageWrapper by lazyUnsafe {
            ImageWrapper.FromRes(R.drawable.ic_journey_calendar_event_slip)
        }
    }

    data object QuitStart : DayEventUiModel {

        override val icon: ImageWrapper by lazyUnsafe {
            ImageWrapper.FromRes(R.drawable.ic_journey_calendar_event_quit_start)
        }
    }

    data object Achievement : DayEventUiModel {

        override val icon: ImageWrapper by lazyUnsafe {
            ImageWrapper.FromRes(R.drawable.ic_journey_calendar_event_achievement)
        }
    }

    data object SavingsGoal : DayEventUiModel {

        override val icon: ImageWrapper by lazyUnsafe {
            ImageWrapper.FromRes(R.drawable.ic_journey_calendar_legend_savings)
        }
    }
}
