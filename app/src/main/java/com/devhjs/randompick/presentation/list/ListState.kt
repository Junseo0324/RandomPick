package com.devhjs.randompick.presentation.list

import androidx.compose.runtime.Immutable
import com.devhjs.randompick.domain.model.PickList

@Immutable
data class ListState(
    val lists: List<PickList> = emptyList(),
    val isLoading: Boolean = false,
    val showAddListSheet: Boolean = false,
    val showEditListSheet: Boolean = false,
    val selectedList: PickList? = null
)
