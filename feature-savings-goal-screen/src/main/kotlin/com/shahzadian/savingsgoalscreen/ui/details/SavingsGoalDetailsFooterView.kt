package com.shahzadian.savingsgoalscreen.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R

@Composable
internal fun SavingsGoalDetailsFooterView(
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        EditButton(
            onClick = onEditClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        DeleteButton(
            onClick = onDeleteClicked,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EditButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onButtonClick = remember { { onClick() } }
    Button(
        onClick = onButtonClick,
        modifier = modifier
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_details_edit),
                contentDescription = "Edit",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )
            Text(
                text = stringResource(R.string.savings_goal_details_edit),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
private fun DeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onButtonClick = remember { { onClick() } }
    Button(
        onClick = onButtonClick,
        modifier = modifier
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_details_delete),
                contentDescription = "Delete",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )
            Text(
                text = stringResource(R.string.savings_goal_details_delete),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
