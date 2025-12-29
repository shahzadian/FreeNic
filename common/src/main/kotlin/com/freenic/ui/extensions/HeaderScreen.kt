package com.freenic.ui.extensions

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
fun HeaderScreen(
    modifier: Modifier = Modifier,
    title: TextWrapper,
    subtitle: TextWrapper? = null,
    hasBackIcon: Boolean = false,
    onBackIconClicked: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    HeaderScreen(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        hasBackIcon = hasBackIcon,
        onBackIconClicked = onBackIconClicked,
        image = null,
        content = content
    )
}

@Composable
fun HeaderScreen(
    modifier: Modifier = Modifier,
    title: TextWrapper,
    subtitle: TextWrapper,
    icon: ImageWrapper? = null,
    hasBackIcon: Boolean = false,
    onBackIconClicked: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    HeaderScreen(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        hasBackIcon = hasBackIcon,
        onBackIconClicked = onBackIconClicked,
        image = {
            icon?.let {
                Image(
                    painter = imageFromWrapper(it),
                    contentDescription = "Header image",
                )
            }
        },
        content = content
    )
}

@Composable
fun HeaderScreen(
    title: TextWrapper,
    modifier: Modifier = Modifier,
    subtitle: TextWrapper? = null,
    hasBackIcon: Boolean = false,
    onBackIconClicked: () -> Unit = {},
    image: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (hasBackIcon) {
                Image(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            onBackIconClicked()
                        }
                )
            }
            HeaderView(
                title = title,
                subtitle = subtitle,
                image = image,
            )
        }
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            content()
        }
    }
}

@Composable
internal fun HeaderView(
    title: TextWrapper,
    subtitle: TextWrapper? = null,
    image: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        TitleView(
            title = title,
            subtitle = subtitle,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )
        image?.let {
            image()
        }
    }
}

@Composable
private fun TitleView(
    title: TextWrapper,
    subtitle: TextWrapper? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = textFromWrapper(title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom =
                subtitle?.let { 4.dp } ?: 0.dp
            ),
        )
        subtitle?.let { subtitle ->
            Text(
                text = textFromWrapper(subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }

    }
}
