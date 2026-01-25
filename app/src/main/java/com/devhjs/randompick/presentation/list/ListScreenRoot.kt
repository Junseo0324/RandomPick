package com.devhjs.randompick.presentation.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.randompick.domain.model.ListEvent
import com.devhjs.randompick.presentation.componenets.Header
import com.devhjs.randompick.presentation.list.components.AddListBottomSheet
import com.devhjs.randompick.presentation.list.components.EditListBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenRoot(
    viewModel: ListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var newListTitle by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ListEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }



    Scaffold(
        topBar = {
            Header("내 리스트", "리스트를 관리하세요")
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAction(ListAction.OnAddListClick) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "리스트 추가")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        ListScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(innerPadding)
        )

        if (state.showEditListSheet && state.selectedList != null) {
            EditListBottomSheet(
                list = state.selectedList,
                onDismiss = {
                    viewModel.onAction(ListAction.OnEditListDismiss)
                },
                onUpdateList = { list ->
                    viewModel.onAction(ListAction.OnUpdateList(list))
                },
                onDeleteList = { list ->
                    viewModel.onAction(ListAction.OnDeleteList(list))
                },
                onAddItem = { listId, name ->
                    viewModel.onAction(ListAction.OnAddItem(listId, name))
                },
                onDeleteItem = { item ->
                    viewModel.onAction(ListAction.OnDeleteItem(item))
                },
                onShowError = { message ->
                    viewModel.onAction(ListAction.OnShowError(message))
                }
            )
        }

        AddListBottomSheet(
            sheetState = sheetState,
            showBottomSheet = state.showAddListSheet,
            newListTitle = newListTitle,
            onTitleChange = { newListTitle = it },
            onDismiss = {
                viewModel.onAction(ListAction.OnAddListDismiss)
                newListTitle = ""
            },
            onAddClick = {
                if (newListTitle.isNotBlank()) {
                    viewModel.onAction(ListAction.OnAddListConfirm(newListTitle.trim()))
                    newListTitle = ""
                }
            }
        )
    }
}
