package com.devhjs.randompick.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devhjs.randompick.core.ui.componenets.Header
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.feature.main.MainViewModel
import com.devhjs.randompick.feature.main.components.BannerAdView
import com.devhjs.randompick.feature.main.components.ListDropdownSheet
import com.devhjs.randompick.feature.main.components.RandomPickContent
import com.devhjs.randompick.feature.main.components.RouletteContent
import com.devhjs.randompick.feature.main.components.TabSelector
import com.devhjs.randompick.navigation.data.Screen

@Composable
fun MainScreen(
    navController: NavController
) {
    val viewModel: MainViewModel = hiltViewModel()
    val lists by viewModel.lists.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLists()
    }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedListIndex by remember { mutableIntStateOf(0) }

    if (lists.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "리스트를 불러오는 중...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        return
    }

    val currentList = lists[selectedListIndex]
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header("랜덤픽", "선택 장애? 랜덤 해결!")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .padding(Dimens.screenPadding)
        ) {
            TabSelector(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(modifier = Modifier.height(Dimens.spacingLarge))
            ListDropdownSheet(
                currentList = currentList,
                allLists = lists,
                onListSelected = { index ->
                    selectedListIndex = index
                }
            )

            Spacer(modifier = Modifier.height(Dimens.spacingExtraLarge))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                when (selectedTab) {
                    0 -> RouletteContent(
                        currentList.items.map { it.name },
                        onAddItemClick = {
                            navController.navigate(Screen.List.route)
                        }
                    )

                    1 -> RandomPickContent(
                        currentList.items.map { it.name },
                        onAddItemClick = {
                            navController.navigate(Screen.List.route)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.spacingSmall))
            BannerAdView(
                adUnitId = "ca-app-pub-3940256099942544/6300978111",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
