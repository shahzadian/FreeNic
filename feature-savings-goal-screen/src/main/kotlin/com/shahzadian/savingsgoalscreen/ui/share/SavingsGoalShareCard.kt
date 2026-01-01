package com.shahzadian.savingsgoalscreen.ui.share

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shahzadian.savingsgoalscreen.R

@Composable
internal fun SavingsGoalShareCard(
    isSelected: Boolean,
    shouldRecord: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRecorded: (Bitmap) -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val graphicsLayer = rememberGraphicsLayer()
    val cardClick = remember(Unit) { { onCardClick() } }
    Card(
        modifier = modifier
            .let {
                if (shouldRecord) {
                    it
                        .drawWithContent {
                            drawContent()
                            graphicsLayer.record {
                                this@drawWithContent.drawContent()
                            }
                        }
                } else {
                    it
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = if (shouldRecord) 0.dp else 4.dp),
        border = if (isSelected && !shouldRecord) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        } else {
            null
        },
        shape = if (shouldRecord) {
            RectangleShape
        } else {
            MaterialTheme.shapes.small
        },
        onClick = cardClick
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            content()
            if (isSelected && !shouldRecord) {
                Image(
                    painter = painterResource(R.drawable.ic_share_achievement_check),
                    contentDescription = "check",
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
    if (shouldRecord) {
        LaunchedEffect(Unit) {
            val image = graphicsLayer.toImageBitmap().asAndroidBitmap()
            onRecorded(image)
        }
    }
}
