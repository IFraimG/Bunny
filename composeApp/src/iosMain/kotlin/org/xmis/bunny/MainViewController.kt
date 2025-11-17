package org.xmis.bunny

import androidx.compose.ui.window.ComposeUIViewController
import org.xmis.bunny.domain.di.initKoin
import org.xmis.bunny.presentation.app.App

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }