package com.shahzadian.freenic.activityeventhandler

internal interface ActivityCommand {

    data class ShowAppRateView(
        val onAppRated: (starsCount: Int) -> Unit,
        val onNotNowClicked: () -> Unit,
    ) : ActivityCommand

    data class OpenUrl(
        val url: String,
    ) : ActivityCommand

    data class PushPermissionRequestCommand(
        val onPermissionGranted: () -> Unit,
        val onPermissionDenied: () -> Unit,
        val onCancelClicked: () -> Unit,
    ) : ActivityCommand
}
