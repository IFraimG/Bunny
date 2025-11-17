package org.xmis.bunny.presentation.ui.password.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.xmis.bunny.data.storages.entities.PasswordEntity

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun PasswordItem(
    passwordData: PasswordEntity,
    deleteItem: (passwordID: Long) -> Unit
) {
    val isShowPassword = remember { mutableStateOf(false) }
    val hidePasswordTemplate = (List(passwordData.password.length) { '*' }).joinToString(separator = "")

    val hidePassword = remember { mutableStateOf(hidePasswordTemplate) }

    fun changeShowPassword() {
        isShowPassword.value = !isShowPassword.value

        if (isShowPassword.value) hidePassword.value = passwordData.password
        else hidePassword.value = hidePasswordTemplate
    }

    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.padding(10.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)) {

            Text(text = passwordData.title,
                modifier = Modifier
                    .width(80.dp))
            Text(text = hidePassword.value,
                modifier = Modifier

                    .width(80.dp))
            Text("description",
                modifier = Modifier
                    .width(80.dp))
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                FloatingActionButton(onClick = { changeShowPassword() },
                    modifier = Modifier
                        .size(48.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Preview,
                        contentDescription = "Show",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
                FloatingActionButton(onClick = { deleteItem(passwordData.id) },
                    modifier = Modifier
                        .size(48.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Remove",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
                FloatingActionButton(onClick = {},
                    modifier = Modifier
                        .size(48.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}