package com.shahzadian.freenic.activityeventhandler

internal interface ActivityCommand {

    data class ShowAppRateView(
        val onAppRated: (starsCount: Int) -> Unit,
        val onNotNowClicked: () -> Unit,
    ) : ActivityCommand
}
