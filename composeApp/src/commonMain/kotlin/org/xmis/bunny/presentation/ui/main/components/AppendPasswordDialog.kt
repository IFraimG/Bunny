package org.xmis.bunny.presentation.ui.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import org.xmis.bunny.presentation.models.PasswordData

@Composable
fun AppendPasswordDialog(
    onConfirmation: (data: PasswordData) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val textTitle = remember { mutableStateOf("") }
    val textPassword = remember { mutableStateOf("") }
    val textDescription = remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    AlertDialog(
        modifier = Modifier.fillMaxWidth(0.92f),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                if (textPassword.value.isNotEmpty() && textTitle.value.isNotEmpty()) {
                    onConfirmation(
                        PasswordData(
                            title = textTitle.value,
                            password = textPassword.value,
                            description = textDescription.value
                        ))
                }
            }) {
                Text(text = "Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Отменить")
            }
        },
        title = {
            Text(text = "Добавить пароль", fontSize = 18.sp)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)) {
                TextField(
                    value = textTitle.value,
                    onValueChange = { textTitle.value = it },
                    label = { Text("Название...") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                )
                TextField(
                    value = textPassword.value,
                    onValueChange = { textPassword.value = it },
                    label = { Text("Пароль...") },
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Показать пароль",
                        modifier = Modifier.clickable {
                            showPassword = !showPassword
                        }
                    )
                    Checkbox(
                        checked = showPassword,
                        onCheckedChange = { showPassword = it }
                    )
                }

                TextField(
                    value = textDescription.value,
                    onValueChange = { textDescription.value = it },
                    label = { Text("Описание (дополнительно)") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        })
}