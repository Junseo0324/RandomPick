package com.devhjs.randompick.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.feature.main.MainViewModel
import com.devhjs.randompick.feature.main.components.ListDropdown
import com.devhjs.randompick.feature.main.components.RandomPickContent
import com.devhjs.randompick.feature.main.components.RouletteContent
import com.devhjs.randompick.feature.main.components.TabSelector

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val lists by viewModel.lists.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLists()
    }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedListIndex by remember { mutableIntStateOf(0) }
    var isListExpanded by remember { mutableStateOf(false) }

    if (lists.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            androidx.compose.material3.Text(
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
        Spacer(modifier = Modifier.height(Dimens.spacingLarge))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(
                        topStart = Dimens.spacingExtraLarge,
                        topEnd = Dimens.spacingExtraLarge
                    )
                )
                .padding(Dimens.screenPadding)
        ) {
            TabSelector(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(modifier = Modifier.height(Dimens.spacingLarge))
            ListDropdown(
                currentList = currentList,
                allLists = lists,
                isExpanded = isListExpanded,
                onExpandChange = { isListExpanded = it },
                onListSelected = { index ->
                    selectedListIndex = index
                    isListExpanded = false
                }
            )

            Spacer(modifier = Modifier.height(Dimens.spacingExtraLarge))

            when (selectedTab) {
                0 -> RouletteContent(currentList.items.map { it.name })
                1 -> RandomPickContent(currentList.items.map { it.name })
            }
        }
    }
}
