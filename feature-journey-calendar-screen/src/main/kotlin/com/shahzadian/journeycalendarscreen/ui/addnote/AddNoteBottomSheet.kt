package com.shahzadian.journeycalendarscreen.ui.addnote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.freenic.ui.wrapper.TextWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddNoteBottomSheet(
    dayText: TextWrapper,
    onNoteAdded: () -> Unit,
    onSheetDismissRequested: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissRequest = remember {
        { onSheetDismissRequested() }
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    ModalBottomSheet(
        onDismissRequest = dismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        AddNoteLockedView(
            dayText = dayText,
            onUnlockClicked = {},
            onCloseClicked = dismissRequest,
            onNotNowClicked = dismissRequest,
            modifier = Modifier.fillMaxSize()
        )
    }
}
