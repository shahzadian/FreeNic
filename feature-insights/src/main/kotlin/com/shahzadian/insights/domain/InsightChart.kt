package com.shahzadian.insights.domain

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

class InsightChartsBlock(
    val id: String,
    val title: TextWrapper,
    val icon: ImageWrapper,
    val charts: List<InsightChart>,
)

sealed interface InsightChart {

    val id: String

    interface AchievableChart : InsightChart {

        val title: TextWrapper
        val description: TextWrapper
        val icon: ImageWrapper
        val badge: InsightChartBadge
        val achieveDateMillis: Long
    }

    interface SingleChart : InsightChart

    class TitleAndPercentsChart(
        override val id: String,
//        TODO
        override val achieveDateMillis: Long,
        override val title: TextWrapper,
        override val description: TextWrapper,
        override val icon: ImageWrapper,
        override val badge: InsightChartBadge,
        val percents: Int,
    ) : AchievableChart

    class LungsRecoveryChart(
        override val id: String,
        override val achieveDateMillis: Long,
        override val title: TextWrapper,
        override val description: TextWrapper,
        override val icon: ImageWrapper,
        override val badge: InsightChartBadge,
        val currentPercents: Int,
    ) : AchievableChart

    class HeartRecoveryChart(
        override val id: String,
        override val achieveDateMillis: Long,
        override val title: TextWrapper,
        override val description: TextWrapper,
        override val icon: ImageWrapper,
        override val badge: InsightChartBadge,
        val currentPercents: Int,
    ) : AchievableChart

    class SlipCasesChart(
        override val id: String,
        val items: List<Item>,
    ) : SingleChart {
        class Item(
            val label: TextWrapper,
            val fixedSlipsCount: Int,
            val fixedCravingsCount: Int,
        )
    }

    class Params(
        val chartPeriod: InsightChartPeriod,
    ) {

        companion object {
            fun default() = Params(InsightChartPeriod.Week)
        }
    }

    sealed interface InsightChartPeriod {

        data object Week : InsightChartPeriod
    }
}

sealed interface InsightChartBadge {

    val text: TextWrapper

    object MedicalEvidence : InsightChartBadge {

        override val text: TextWrapper by lazyUnsafe {
            TextWrapper.FromRes(R.string.insights_badge_medical)
        }
    }

    object Who : InsightChartBadge {
        override val text: TextWrapper by lazyUnsafe {
            TextWrapper.FromRes(R.string.insights_badge_who)
        }
    }

    object Studies : InsightChartBadge {
        override val text: TextWrapper by lazyUnsafe {
            TextWrapper.FromRes(R.string.insights_badge_studies)
        }
    }

    object Estimated : InsightChartBadge {
        override val text: TextWrapper by lazyUnsafe {
            TextWrapper.FromRes(R.string.insights_badge_estimated)
        }
    }
}
