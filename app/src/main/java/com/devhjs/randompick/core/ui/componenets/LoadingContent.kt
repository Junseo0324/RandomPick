package com.devhjs.randompick.core.ui.componenets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.randompick.core.ui.theme.Dimens

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingExtraLarge),
    ) {
        CircularProgressIndicator()
    }
}