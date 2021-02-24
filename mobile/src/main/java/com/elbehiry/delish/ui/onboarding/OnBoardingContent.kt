package com.elbehiry.delish.ui.onboarding

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elbehiry.delish.R
import com.elbehiry.delish.ui.main.launchMainActivity
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@VisibleForTesting
@Composable
fun OnBoardingContent(
    viewModel: OnBoardingViewModel,
    onBoardingFinished: () -> Unit
) {
    val launchDestination: Boolean by viewModel.navigateToMainActivity.observeAsState(false)

    if (launchDestination) {
        launchMainActivity(context = AmbientContext.current)
        onBoardingFinished()
    }

    Scaffold(
        topBar = { OnBoardingTopBar(viewModel) },
        backgroundColor = MaterialTheme.colors.primarySurface
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .statusBarsPadding()
        ) {

            Text(
                text = "this is the first page of onBoarding",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 32.dp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            val state = remember { mutableStateOf(RotationStates.Original) }

            Button(
                onClick = {
                    state.value = RotationStates.Rotated
                }) {
                Text(text = "Retry")
            }

            Spacer(modifier = Modifier.height(16.dp))

            val transition = updateTransition(targetState = state.value)
            val rotation by transition.animateFloat({
                tween(durationMillis = 1000)
            }) {
                if (it == RotationStates.Original) 0f else 360f
            }

            Box(contentAlignment = Alignment.Center) {
                Image(
                    bitmap = imageResource(id = R.drawable.empty_plate),
                    contentDescription = null,
                    modifier = Modifier
                        .preferredSize(250.dp, 250.dp)
                        .rotate(rotation)
                )
                Image(
                    bitmap = imageResource(id = R.drawable.ic_health_food),
                    contentDescription = null,
                    modifier = Modifier
                        .preferredSize(100.dp, 100.dp)
                )
            }
        }
    }
}

@Composable
fun OnBoardingTopBar(
    viewModel: OnBoardingViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Image(
            imageVector = vectorResource(id = R.drawable.ic_lockup_white),
            contentDescription = null,
            modifier = Modifier.padding(16.dp)
        )
        IconButton(
            modifier = Modifier.padding(16.dp),
            onClick = { viewModel.getStartedClick() }) {
            Text(text = "Skip")
        }
    }
}

private enum class RotationStates {
    Original,
    Rotated
}