package com.shahzadian.datafill.domain.fillableInfo.sharedstate

interface FillableDataSharedStateHolder {

    var sharedState: FillableInfoSharedState

    companion object {
        operator fun invoke(): FillableDataSharedStateHolder = FillableDataSharedStateHolderImpl()
    }
}

private class FillableDataSharedStateHolderImpl : FillableDataSharedStateHolder {

    override var sharedState: FillableInfoSharedState = FillableInfoSharedState()
}