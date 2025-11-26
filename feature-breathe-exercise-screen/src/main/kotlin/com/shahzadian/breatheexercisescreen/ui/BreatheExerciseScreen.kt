package com.shahzadian.breatheexercisescreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shahzadian.breatheexercisescreen.R
import com.shahzadian.breatheexercisescreen.ui.vuewmodel.BreatheExerciseViewModel
import com.shahzadian.breatheexercisescreen.ui.vuewmodel.BreatheExerciseViewState

@Composable
internal fun breatheExerciseBackground(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFF5FAFF),
            Color(0xFFD1F5FF),
            Color(0xFF81D4FA),
        ),
    )
}

@Composable
fun BreatheExerciseScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: BreatheExerciseViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is BreatheExerciseViewState.Loading -> {}
        is BreatheExerciseViewState.Data -> {
            ContentView(
                data = (state as BreatheExerciseViewState.Data),
                modifier = modifier
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: BreatheExerciseViewState.Data,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(breatheExerciseBackground()),
    ) {
        HeaderView(
            data = data,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun HeaderView(
    data: BreatheExerciseViewState.Data,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_breathe_exercise_header),
                contentDescription = "header",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(
                    com.shahzadian.baseui.R.string.breathe_exercise_header_title,
                    data.inhaleSeconds, data.holdSeconds, data.exhaleSeconds,
                ),
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.breathe_exercise_header_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
