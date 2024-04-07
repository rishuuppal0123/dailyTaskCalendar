package com.example.calender.commonComposables

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

fun Modifier.`if`(
    condition: Boolean,
    then: (Modifier) -> Modifier,
): Modifier =
    if (condition) {
        then(this)
    } else {
        this
    }

@Composable
fun Modifier.customClickable(
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = this.clickable(
    interactionSource = interactionSource,
    indication = indication,
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = onClick
)