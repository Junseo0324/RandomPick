package com.devhjs.randompick.domain.model

sealed class ListEvent {
    data class ShowMessage(val message: String) : ListEvent()
}