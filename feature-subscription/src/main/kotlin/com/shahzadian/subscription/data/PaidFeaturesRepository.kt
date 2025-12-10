package com.shahzadian.subscription.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.subscription.R
import com.shahzadian.subscription.domain.PaidFeature
import javax.inject.Inject

internal interface PaidFeaturesRepository {

    suspend fun getPaidFeatures(): List<PaidFeature>
}

internal class PaidFeaturesRepositoryImpl @Inject constructor() : PaidFeaturesRepository {

    override suspend fun getPaidFeatures(): List<PaidFeature> {
        return listOf(
            PaidFeature(
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_no_ads_title),
                description = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_no_ads_subtitle),
                icon = ImageWrapper.FromRes(R.drawable.ic_paywall_feature_no_ads),
            ),
            PaidFeature(
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_goal_tracker_title),
                description = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_goal_tracker_subtitle),
                icon = ImageWrapper.FromRes(R.drawable.ic_paywall_feature_goal_tracker),
            ),
            PaidFeature(
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_widget_title),
                description = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_widget_subtitle),
                icon = ImageWrapper.FromRes(R.drawable.ic_paywall_feature_widget),
            ),
            PaidFeature(
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_motivation_notes_title),
                description = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_motivation_notes_subtitle),
                icon = ImageWrapper.FromRes(R.drawable.ic_paywall_feature_motivation_notes),
            ),
            PaidFeature(
                name = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_analytics_title),
                description = TextWrapper.FromRes(com.shahzadian.baseui.R.string.paywall_feature_analytics_subtitle),
                icon = ImageWrapper.FromRes(R.drawable.ic_paywall_feature_analytics),
            )
        )
    }
}
