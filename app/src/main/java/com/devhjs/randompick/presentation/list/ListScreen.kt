package com.devhjs.randompick.presentation.list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devhjs.randompick.BuildConfig
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.core.ui.theme.RandomPickTheme
import com.devhjs.randompick.presentation.list.components.ListCard
import com.devhjs.randompick.presentation.main.components.BannerAdView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    state: ListState = ListState(),
    onAction: (ListAction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = Dimens.spacingSmall)
                .weight(1f),
        ) {
            items(state.lists) { list ->
                ListCard(
                    list = list,
                    onClick = {
                        onAction(ListAction.OnListClick(list))
                    }
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            BannerAdView(
                adUnitId = BuildConfig.AD_UNIT_ID,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}


@Preview(
    name = "1. Light Mode Preview",
    group = "ListScreen Previews",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun ListScreenLightPreview() {
    RandomPickTheme(darkTheme = false) {
        ListScreen()
    }
}

@Preview(
    name = "2. Dark Mode Preview",
    group = "ListScreen Previews",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun ListScreenDarkPreview() {
    RandomPickTheme(darkTheme = true) {
        ListScreen()
    }
}

