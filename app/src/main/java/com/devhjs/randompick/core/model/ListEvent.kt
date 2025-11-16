package com.devhjs.randompick.core.model

sealed class ListEvent {
    data class ShowMessage(val message: String) : ListEvent()
}