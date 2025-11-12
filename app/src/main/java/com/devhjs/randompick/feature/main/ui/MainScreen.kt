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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.feature.main.components.ListDropdown
import com.devhjs.randompick.feature.main.components.RandomPickContent
import com.devhjs.randompick.feature.main.components.RouletteContent
import com.devhjs.randompick.feature.main.components.TabSelector

data class FoodList(
    val name: String,
    val items: List<String>
)

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedListIndex by remember { mutableIntStateOf(0) }
    var isListExpanded by remember { mutableStateOf(false) }

    val foodLists = remember {
        listOf(
            FoodList("점심 메뉴", listOf("김치찌개", "햄버거", "라면", "피자", "만두")),
            FoodList("저녁 메뉴", listOf("치킨", "족발", "회", "삼겹살"))
        )
    }

    val currentList = foodLists[selectedListIndex]
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
                allLists = foodLists,
                isExpanded = isListExpanded,
                onExpandChange = { isListExpanded = it },
                onListSelected = { index ->
                    selectedListIndex = index
                    isListExpanded = false
                }
            )

            Spacer(modifier = Modifier.height(Dimens.spacingExtraLarge))

            when (selectedTab) {
                0 -> RouletteContent(currentList.items)
                1 -> RandomPickContent(currentList.items)
            }
        }
    }
}
