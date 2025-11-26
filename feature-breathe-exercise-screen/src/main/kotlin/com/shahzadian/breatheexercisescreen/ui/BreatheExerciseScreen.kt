package com.shahzadian.breatheexercisescreen.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.breatheexercisescreen.R
import com.shahzadian.breatheexercisescreen.ui.vuewmodel.BreatheExerciseViewEvent
import com.shahzadian.breatheexercisescreen.ui.vuewmodel.BreatheExerciseViewModel
import com.shahzadian.breatheexercisescreen.ui.vuewmodel.BreatheExerciseViewState
import kotlinx.coroutines.delay

private const val CIRCLE_TARGET_SCALE: Float = 1.5f

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
    val onEvent: (BreatheExerciseViewEvent) -> Unit = remember(viewModel) {
        { viewModel.onViewEvent(it) }
    }
    when (state) {
        is BreatheExerciseViewState.Loading -> {}
        is BreatheExerciseViewState.Process -> {
            ContentView(
                data = (state as BreatheExerciseViewState.Process),
                onCircleClick = {
                    onEvent(BreatheExerciseViewEvent.OnCircleClick)
                },
                onInhaleFinish = {
                    onEvent(BreatheExerciseViewEvent.OnInhaleFinish)
                },
                onHoldFinish = {
                    onEvent(BreatheExerciseViewEvent.OnHoldFinish)
                },
                onExhaleFinish = {
                    onEvent(BreatheExerciseViewEvent.OnExhaleFinish)
                },
                modifier = modifier
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: BreatheExerciseViewState.Process,
    onCircleClick: () -> Unit,
    onInhaleFinish: () -> Unit,
    onHoldFinish: () -> Unit,
    onExhaleFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val click: () -> Unit = remember(data) {
        { onCircleClick() }
    }
    val inhaleFinish: () -> Unit = remember(data) {
        { onInhaleFinish() }
    }
    val holdFinish: () -> Unit = remember(data) {
        { onHoldFinish() }
    }
    val exhaleFinish: () -> Unit = remember(data) {
        { onExhaleFinish() }
    }
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
        CircleView(
            data = data,
            onClick = click,
            onInhaleFinish = inhaleFinish,
            onHoldFinish = holdFinish,
            onExhaleFinish = exhaleFinish,
            modifier = Modifier
                .align(Alignment.Center)
        )
        if (data.exerciseState != BreatheExerciseViewState.ExerciseState.READY_FOR_EXERCISE) {
            FooterView(
                data = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun HeaderView(
    data: BreatheExerciseViewState.Process,
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

@Composable
private fun CircleView(
    data: BreatheExerciseViewState.Process,
    onClick: () -> Unit,
    onInhaleFinish: () -> Unit,
    onHoldFinish: () -> Unit,
    onExhaleFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val circleClick: () -> Unit = remember(data) {
        { onClick() }
    }
    val inhaleFinish: () -> Unit = remember(data) {
        { onInhaleFinish() }
    }
    val holdFinish: () -> Unit = remember(data) {
        { onHoldFinish() }
    }
    val exhaleFinish: () -> Unit = remember(data) {
        { onExhaleFinish() }
    }
    val applyCircleModifier: @Composable Modifier.() -> Modifier = remember {
        {
            this
                .width(200.dp)
                .height(200.dp)
                .clickable(onClick = circleClick)
        }
    }
    when (data.exerciseState) {
        BreatheExerciseViewState.ExerciseState.READY_FOR_EXERCISE -> {
            Box(
                modifier = modifier
            ) {
                ReadyToStartStateCircleView(
                    applyModifier = applyCircleModifier,
                    modifier = modifier,
                )
                CircleTextView(
                    data = data,
                    onInhaleFinish = onInhaleFinish,
                    onHoldFinish = holdFinish,
                    onExhaleFinish = onExhaleFinish,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        BreatheExerciseViewState.ExerciseState.INHALE -> {
            Box(
                modifier = modifier,
            ) {
                InhaleCircleView(
                    inhaleSeconds = data.inhaleSeconds,
                    applyModifier = applyCircleModifier,
                    modifier = modifier,
                )
                CircleTextView(
                    data = data,
                    onInhaleFinish = inhaleFinish,
                    onHoldFinish = holdFinish,
                    onExhaleFinish = exhaleFinish,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        BreatheExerciseViewState.ExerciseState.HOLD -> {
            Box(
                modifier = modifier,
            ) {
                HoldCircleView(
                    applyModifier = applyCircleModifier,
                    modifier = modifier,
                )
                CircleTextView(
                    data = data,
                    onInhaleFinish = inhaleFinish,
                    onHoldFinish = holdFinish,
                    onExhaleFinish = exhaleFinish,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        BreatheExerciseViewState.ExerciseState.EXHALE -> {
            Box(
                modifier = modifier,
            ) {
                ExhaleCircleView(
                    exhaleSeconds = data.exhaleSeconds,
                    applyModifier = applyCircleModifier,
                    modifier = modifier,
                )
                CircleTextView(
                    data = data,
                    onInhaleFinish = inhaleFinish,
                    onHoldFinish = holdFinish,
                    onExhaleFinish = onExhaleFinish,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun CircleTextView(
    data: BreatheExerciseViewState.Process,
    onInhaleFinish: () -> Unit,
    onHoldFinish: () -> Unit,
    onExhaleFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (data.exerciseState) {
        BreatheExerciseViewState.ExerciseState.READY_FOR_EXERCISE -> {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.breathe_exercise_start_button),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier,
            )
        }

        BreatheExerciseViewState.ExerciseState.INHALE -> {
            val inhaleSeconds = data.inhaleSeconds
            var currentValue by remember { mutableStateOf(0) }
            LaunchedEffect(currentValue) {
                delay(1000)
                if (currentValue < inhaleSeconds - 1) {
                    currentValue += 1
                } else {
                    onInhaleFinish()
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier,
            ) {
                Text(
                    text = stringResource(com.shahzadian.baseui.R.string.breathe_exercise_inhale),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 4.dp),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = currentValue.toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }

        BreatheExerciseViewState.ExerciseState.HOLD -> {
            val holdSeconds = data.holdSeconds
            var currentValue by remember { mutableStateOf(0) }
            LaunchedEffect(currentValue) {
                delay(1000)
                if (currentValue < holdSeconds - 1) {
                    currentValue += 1
                } else {
                    onHoldFinish()
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier,
            ) {
                Text(
                    text = stringResource(com.shahzadian.baseui.R.string.breathe_exercise_hold),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = modifier,
                )
                Text(
                    text = currentValue.toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }

        BreatheExerciseViewState.ExerciseState.EXHALE -> {
            val exhaleSeconds = data.exhaleSeconds
            var currentValue by remember { mutableStateOf(0) }
            LaunchedEffect(currentValue) {
                delay(1000)
                if (currentValue < exhaleSeconds - 1) {
                    currentValue += 1
                } else {
                    onExhaleFinish()
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier,
            ) {
                Text(
                    text = stringResource(com.shahzadian.baseui.R.string.breathe_exercise_exhale),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                Text(
                    text = currentValue.toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun ReadyToStartStateCircleView(
    applyModifier: @Composable Modifier.() -> Modifier,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "ready to start state circle view")
    val scale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "ready to start state circle view"
    )
    Box(
        modifier = modifier
            .scale(scale)
            .clip(CircleShape)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                    ),
                )
            )
            .applyModifier()
    )
}

@Composable
private fun InhaleCircleView(
    inhaleSeconds: Int,
    applyModifier: @Composable Modifier.() -> Modifier,
    modifier: Modifier = Modifier,
) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(inhaleSeconds) {
        startAnimation = true
    }
    val durationMillis = (inhaleSeconds.coerceAtLeast(0) * 1000).coerceAtLeast(1)
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) CIRCLE_TARGET_SCALE else 1f,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = LinearEasing,
        ),
        label = "inhale circle view scale"
    )
    Box(
        modifier = modifier
            .scale(scale)
            .clip(CircleShape)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                    ),
                )
            )
            .applyModifier()
    )
}

@Composable
private fun HoldCircleView(
    applyModifier: @Composable Modifier.() -> Modifier,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .scale(CIRCLE_TARGET_SCALE)
            .clip(CircleShape)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                    ),
                )
            )
            .applyModifier()
    )
}

@Composable
private fun ExhaleCircleView(
    exhaleSeconds: Int,
    applyModifier: @Composable Modifier.() -> Modifier,
    modifier: Modifier = Modifier,
) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(exhaleSeconds) {
        startAnimation = true
    }
    val durationMillis = (exhaleSeconds.coerceAtLeast(0) * 1000).coerceAtLeast(1)
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else CIRCLE_TARGET_SCALE,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "inhale circle view scale"
    )
    Box(
        modifier = modifier
            .scale(scale)
            .clip(CircleShape)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                    ),
                )
            )
            .applyModifier()
    )
}

@Composable
private fun FooterView(
    data: BreatheExerciseViewState.Process,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CycleTextView(
            data = data,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        HelpingTextView(data = data)
    }
}

@Composable
private fun CycleTextView(
    data: BreatheExerciseViewState.Process,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(
            com.shahzadian.baseui.R.string.breathe_exercise_cycle_of,
            data.currentCycle,
            data.cyclesCount
        ),
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    )
}

@Composable
private fun HelpingTextView(
    data: BreatheExerciseViewState.Process,
    modifier: Modifier = Modifier,
) {
    val texts = data.helpingTexts
    if (texts.isEmpty()) return
    var currentTextIndex by remember { mutableStateOf(0) }
    LaunchedEffect(currentTextIndex) {
        delay(8000)
        if (currentTextIndex < texts.size - 1) {
            currentTextIndex += 1
        } else {
            currentTextIndex = 0
        }
    }
    Text(
        text = textFromWrapper(texts[currentTextIndex]),
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier,
    )
}