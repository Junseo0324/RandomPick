package com.devhjs.randompick.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devhjs.randompick.BuildConfig
import com.devhjs.randompick.core.ui.componenets.Header
import com.devhjs.randompick.core.ui.componenets.LoadingContent
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.feature.main.MainViewModel
import com.devhjs.randompick.feature.main.components.BannerAdView
import com.devhjs.randompick.feature.main.components.EmptyPickContent
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

    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLists()
    }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedListIndex by remember { mutableIntStateOf(0) }

    when {
        isLoading -> {
            LoadingContent()
        }
        lists.isEmpty() -> {
            EmptyPickContent(
                message = "리스트를 추가해주세요!",
                buttonText = "리스트 만들러 가기",
                onAddItemClick = {
                    navController.navigate(Screen.List.route)
                }
            )
        }

        else -> {
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
                        adUnitId = BuildConfig.AD_UNIT_ID,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
