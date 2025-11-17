package com.shahzadian.datafill.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.datafill.ui.DataFillViewState

@Composable
internal fun DataFillSmokingPageScreen(
    data: DataFillViewState.SmokingHabits,
    onCigarettesCountChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun CigarettesCountBlock(
    value: String,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        DataFillInputLabel(
            text = TextWrapper.FromRes(R.string.data_fill_smoking_habits_cigarettes_count_label),
            modifier = Modifier.padding(bottom = 8.dp),
        )
    }
}