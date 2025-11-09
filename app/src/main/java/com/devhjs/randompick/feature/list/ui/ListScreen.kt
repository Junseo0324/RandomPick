package com.devhjs.randompick.feature.list.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.devhjs.randompick.core.model.PickList
import com.devhjs.randompick.core.ui.componenets.Header
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.core.ui.theme.RandomPickTheme
import com.devhjs.randompick.feature.list.ListViewModel
import com.devhjs.randompick.feature.list.components.EditListBottomSheet
import com.devhjs.randompick.feature.list.components.ListCard

@Composable
fun ListScreen() {
    val viewModel: ListViewModel = hiltViewModel()

    val lists by viewModel.lists.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    var selectedList by remember { mutableStateOf<PickList?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadLists()
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
            items(lists) { list ->
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
           viewModel = viewModel
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
