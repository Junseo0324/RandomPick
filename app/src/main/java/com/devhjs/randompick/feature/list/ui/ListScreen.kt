package com.devhjs.randompick.feature.list.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devhjs.randompick.core.ui.componenets.Header
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.core.ui.theme.RandomPickTheme
import com.devhjs.randompick.feature.list.components.EditListBottomSheet
import com.devhjs.randompick.feature.list.components.ListCard
import com.devhjs.randompick.feature.main.ui.FoodList

@Composable
fun ListScreen() {
    var showSheet by remember { mutableStateOf(false) }
    var selectedList by remember { mutableStateOf<FoodList?>(null) }

    val foodLists = remember {
        mutableStateListOf(
            FoodList("점심 메뉴", mutableListOf("김치찌개", "햄버거", "라면", "피자", "만두")),
            FoodList("저녁 메뉴", mutableListOf("치킨", "족발", "회", "삼겹살"))
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header("내 리스트","리스트를 관리하세요")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingLarge)
        ) {
            itemsIndexed(foodLists) { index, list ->
                ListCard(
                    list = list,
                    onClick = {
                        selectedList = list
                        showSheet = true
                    }
                )
            }
        }
    }

    if (showSheet && selectedList != null) {
        EditListBottomSheet(
            list = selectedList,
            onDismiss = {
                showSheet = false
                selectedList = null
            },
            onSave = { updated ->
                val idx = foodLists.indexOfFirst { it.name == selectedList?.name }
                if (idx != -1) foodLists[idx] = updated else foodLists.add(updated)
            },
            onDelete = {
                foodLists.remove(selectedList)
            }
        )
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
