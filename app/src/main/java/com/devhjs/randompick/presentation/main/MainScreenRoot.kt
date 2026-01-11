package com.devhjs.randompick.presentation.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.randompick.core.util.AdManager

@Composable
fun MainScreenRoot(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit,
    onNavigateToLicense: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is MainEvent.ShowAd -> {
                    activity?.let { AdManager.showInterstitialAd(it) }
                }
                is MainEvent.NavigateToList -> {
                    onNavigateToList()
                }
                is MainEvent.NavigateToLicense -> {
                    onNavigateToLicense()
                }
            }
        }
    }

    MainScreen(
        state = state,
        onAction = viewModel::onAction
    )
}