package com.devhjs.randompick.presentation.main

sealed interface MainEvent {
    data object ShowAd : MainEvent
    data object NavigateToList : MainEvent
    data object NavigateToLicense : MainEvent
}
