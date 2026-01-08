package com.shahzadian.helpfulnotes.domain

import com.freenic.ui.wrapper.TextWrapper

class HelpfulNote(
    val text: TextWrapper,
    val source: Source,
) {

    sealed interface Source {
        object General : Source
        object ByUser : Source
    }
}
