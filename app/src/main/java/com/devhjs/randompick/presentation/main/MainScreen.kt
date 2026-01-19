package com.devhjs.randompick.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devhjs.randompick.BuildConfig
import com.devhjs.randompick.presentation.componenets.Header
import com.devhjs.randompick.presentation.componenets.LoadingContent
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.presentation.main.components.BannerAdView
import com.devhjs.randompick.presentation.main.components.EmptyPickContent
import com.devhjs.randompick.presentation.main.components.LadderGameContent
import com.devhjs.randompick.presentation.main.components.ListDropdownSheet
import com.devhjs.randompick.presentation.main.components.RandomPickContent
import com.devhjs.randompick.presentation.main.components.RouletteContent
import com.devhjs.randompick.presentation.main.components.TabSelector

@Composable
fun MainScreen(
    state: MainState = MainState(),
    onAction: (MainAction) -> Unit= { },
) {

    when {
        state.isLoading -> {
            LoadingContent()
        }
        state.list.isEmpty() -> {
            EmptyPickContent(
                message = "리스트를 추가해주세요!",
                buttonText = "리스트 만들러 가기",
                onAddItemClick = {
                    onAction(MainAction.OnEmptyButtonClick)
                }
            )
        }

        else -> {
            val currentList = state.list.getOrElse(state.selectedListIndex) { state.list[0] }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Header(
                    title = "랜덤픽",
                    description = "선택 장애? 랜덤 해결!",
                    onLicenseClick = {
                        onAction(MainAction.OnLicenseClick)
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.surface
                        )
                        .padding(Dimens.screenPadding)
                ) {
                    TabSelector(
                        selectedTab = state.selectedTab,
                        onTabSelected = { onAction(MainAction.OnTabSelected(it)) }
                    )

                    Spacer(modifier = Modifier.height(Dimens.spacingLarge))
                    ListDropdownSheet(
                        currentList = currentList,
                        allLists = state.list,
                        onListSelected = { index ->
                            onAction(MainAction.OnListSelected(index))
                        }
                    )

                    Spacer(modifier = Modifier.height(Dimens.spacingExtraLarge))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        when (state.selectedTab) {
                            0 -> RouletteContent(
                                currentList.items.map { it.name },
                                onInteraction = {
                                    onAction(MainAction.OnInteraction)
                                },
                                onAddItemClick = {
                                    onAction(MainAction.OnAddItemClick)
                                }
                            )

                            1 -> RandomPickContent(
                                currentList.items.map { it.name },
                                onInteraction = {
                                    onAction(MainAction.OnInteraction)
                                },
                                onAddItemClick = {
                                    onAction(MainAction.OnAddItemClick)
                                }
                            )

                            2 -> LadderGameContent(
                                items = currentList.items.map { it.name },
                                ladderBridges = state.bridge,
                                gameResult = state.gameResult,
                                onGenerateLadder = { onAction(MainAction.OnGenerateLadder(it)) },
                                onInteraction = {
                                    onAction(MainAction.OnInteraction)
                                },
                                onAddItemClick = {
                                    onAction(MainAction.OnAddItemClick)
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

@Preview
@Composable
private fun MainScreenPrev() {
    MainScreen()
}
