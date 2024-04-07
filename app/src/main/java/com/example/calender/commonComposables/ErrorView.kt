package com.example.calender.commonComposables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calender.R

@Composable
fun ErrorLayout(message: String, btnText: String = "Retry",  retry: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = "error",
            modifier = Modifier
                .size(150.dp)
                .fillMaxSize()
        )
        Text(
            textAlign = TextAlign.Center,
            text = message,
            fontSize = 24.sp
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = retry,
            colors = ButtonDefaults.textButtonColors(containerColor = Color.Red)
        ) {
            Text(
                text = btnText,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 20.sp
            )
        }
    }
}