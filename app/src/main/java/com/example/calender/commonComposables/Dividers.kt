package com.example.calender.commonComposables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calender.ui.theme.PartitionLineColor

@Composable
fun HorizontalDivider( modifier: Modifier = Modifier, color: Color = PartitionLineColor) {
    Divider(thickness = 1.dp, modifier = modifier.fillMaxWidth(), color = color)
}
