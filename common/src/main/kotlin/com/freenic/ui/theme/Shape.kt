package com.freenic.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Material 3 shape scale for FreeNic
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),    // badges, small chips
    small      = RoundedCornerShape(8.dp),    // input fields, small cards
    medium     = RoundedCornerShape(12.dp),   // standard cards, sheets
    large      = RoundedCornerShape(16.dp),   // buttons, dialogs
    extraLarge = RoundedCornerShape(28.dp)    // bottom sheets, full-width containers
)
