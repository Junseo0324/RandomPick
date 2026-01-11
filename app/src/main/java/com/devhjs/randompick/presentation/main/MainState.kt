package com.devhjs.randompick.presentation.main

import androidx.compose.runtime.Stable
import com.devhjs.randompick.domain.model.Bridge
import com.devhjs.randompick.domain.model.PickList

@Stable
data class MainState(
    val list: List<PickList> = emptyList(),
    val isLoading: Boolean = false,
    val bridge:List<Bridge> = emptyList(),
    val gameResult: Map<Int, Int> = emptyMap(),
    val selectedTab: Int = 0,
    val selectedListIndex: Int = 0
)
