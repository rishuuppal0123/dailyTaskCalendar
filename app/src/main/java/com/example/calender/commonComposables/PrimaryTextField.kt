package com.example.calender.commonComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.calender.ui.theme.Purple40
import com.example.calender.ui.theme.grey

@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    maxLines: Int = 2,
    maxLength: Int = 128,
    error: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    placeHolder: String? = null,
    helperText: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    placeHolderStyle: TextStyle = textStyle.copy(color = grey),
    helperTextStyle: TextStyle = MaterialTheme.typography.labelSmall.copy(color = if (error) Purple40 else Color.Black),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    helperPaddingValues: PaddingValues = PaddingValues(start = 16.dp)
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (label != null) {
            Text(text = label, style = labelStyle, modifier = Modifier.padding(bottom = 6.dp))
        }
        BasicTextField(modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val height = size.height
                drawLine(
                    color = Purple40,
                    start = Offset(0f, 0f),
                    end = Offset(0f, height),
                    strokeWidth = 2.dp.toPx()
                )
            },
            value = value,
            onValueChange = {
                if (
                    it.length > maxLength || it == value || (value.isBlank() && it.isBlank())
                    || (it.length >= 2 && it.last()
                        .isWhitespace() && it[it.length - 2].isWhitespace())
                ) {
                    return@BasicTextField
                }
                if (keyboardOptions.keyboardType != KeyboardType.Number) {
                    onValueChange(it)
                } else {
                    if (it.isDigitsOnly()) {
                        onValueChange(it)
                    }
                }
            },
            maxLines = maxLines,
            singleLine = singleLine,
            readOnly = readOnly,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            textStyle = textStyle,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(contentPadding)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty() && placeHolder != null) {
                            Text(text = placeHolder, style = placeHolderStyle)
                        }
                        innerTextField()
                    }
                }
            })
        if (helperText != null) {
            if ((error && value.isNotEmpty()) || !error) {
                Text(
                    text = helperText,
                    style = helperTextStyle,
                    modifier = Modifier.padding(helperPaddingValues)
                )
            }
        }
    }
}