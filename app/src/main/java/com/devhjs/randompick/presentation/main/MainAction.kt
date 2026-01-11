package com.devhjs.randompick.presentation.main

sealed interface MainAction {
    data object OnEmptyButtonClick : MainAction
    data object OnLicenseClick : MainAction
    data object OnAddItemClick : MainAction
    data class OnGenerateLadder(val count: Int) : MainAction
    data object OnInteraction : MainAction
    data class OnTabSelected(val index: Int) : MainAction
    data class OnListSelected(val index: Int) : MainAction
}