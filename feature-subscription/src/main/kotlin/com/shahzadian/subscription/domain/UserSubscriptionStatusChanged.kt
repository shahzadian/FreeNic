package com.shahzadian.subscription.domain

import com.freenic.activityevent.ActivityEventResult

data class UserSubscriptionStatusChanged(
    val status: UserSubscriptionStatus,
) : ActivityEventResult
