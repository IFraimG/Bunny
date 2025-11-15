package org.xmis.bunny.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bunny.composeapp.generated.resources.Res
import bunny.composeapp.generated.resources.chest
import bunny.composeapp.generated.resources.dverkluchiki
import bunny.composeapp.generated.resources.failikidver
import bunny.composeapp.generated.resources.svitok
import bunny.composeapp.generated.resources.zaychik
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.xmis.bunny.presentation.navigation.Destinations
import org.xmis.bunny.presentation.ui.main.components.AppendPasswordDialog

@Composable
@Preview
fun MainScreen(navContoller: NavController,
               viewModel: MainViewModel = viewModel { MainViewModel() }
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        when {
            showDialog -> {
                AppendPasswordDialog(
                    onDismissRequest = { showDialog = false },
                    onConfirmation = { data ->
                        viewModel.savePassword(data)
                        showDialog = false },
                )
            }
        }

        Image(
            painter = painterResource(Res.drawable.zaychik),
            contentDescription = null,
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
        )
        Text(text = "ЗАЙЧИК", fontSize = 32.sp)

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable {
                    if (!showDialog) {
                        showDialog = true
                    }
                }) {
                Image(
                    painter = painterResource(Res.drawable.chest),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                )
                Text("Новый ключик", fontSize = 20.sp)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable {
                    if (!showDialog) {

                    }
                }) {
                Image(
                    painter = painterResource(Res.drawable.svitok),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                )
                Text("Новый свиток", fontSize = 20.sp)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable {
                    if (!showDialog) {

                    }
                }) {
                Image(painter = painterResource(Res.drawable.dverkluchiki),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp))
                Text("ЗНАНИЯ", fontSize = 20.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable {
                    if (!showDialog) {
                        navContoller.navigate(Destinations.OPEN_PASSWORDS)
                    }
                }) {
                Image(painter = painterResource(Res.drawable.failikidver),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp))
                Text("КЛЮЧИКИ", fontSize = 20.sp)
            }
        }
    }
}