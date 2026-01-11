package com.devhjs.randompick.presentation.main

import com.devhjs.randompick.domain.model.Bridge
import com.devhjs.randompick.domain.model.PickList

data class MainState(
    val list: List<PickList> = emptyList(),
    val isLoading: Boolean = false,
    val bridge:List<Bridge> = emptyList(),
    val gameResult: Map<Int, Int> = emptyMap()
)
