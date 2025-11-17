package org.xmis.bunny.presentation.ui.password.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TableHeader() {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(text = "Название",
            modifier = Modifier
                .width(80.dp))
        Text(text = "Пароль",
            modifier = Modifier
                .width(80.dp))
        Text(text = "Описание",
            modifier = Modifier
                .width(80.dp))
    }
}