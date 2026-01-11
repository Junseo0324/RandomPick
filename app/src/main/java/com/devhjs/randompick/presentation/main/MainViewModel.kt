package com.devhjs.randompick.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.randompick.domain.model.Bridge
import com.devhjs.randompick.domain.usecase.GetPickListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPickListsUseCase: GetPickListsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private var interactionCount = 0

    init {
        // Reactive Data Loading
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getPickListsUseCase.execute()
                .collect { lists ->
                    _state.update {
                        it.copy(
                            list = lists,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnEmptyButtonClick -> {
                viewModelScope.launch { _event.emit(MainEvent.NavigateToList) }
            }
            is MainAction.OnLicenseClick -> {
                viewModelScope.launch { _event.emit(MainEvent.NavigateToLicense) }
            }
            is MainAction.OnAddItemClick -> {
                viewModelScope.launch { _event.emit(MainEvent.NavigateToList) }
            }
            is MainAction.OnGenerateLadder -> {
                generateLadder(action.count)
            }
            is MainAction.OnInteraction -> {
                checkAndShowAd()
            }
            is MainAction.OnTabSelected -> {
                _state.update { it.copy(selectedTab = action.index) }
            }
            is MainAction.OnListSelected -> {
                _state.update { it.copy(selectedListIndex = action.index) }
            }
        }
    }

    private fun generateLadder(itemCount: Int) {
        if (itemCount < 2) {
            _state.update { it.copy(bridge = emptyList()) }
            return
        }
        val bridges = mutableListOf<Bridge>()
        val steps = 10

        for (step in 1 until steps) {
            var col = 0
            while (col < itemCount - 1) {
                if (Random.nextBoolean()) {
                    bridges.add(Bridge(col, step))
                    col += 2
                } else {
                    col++
                }
            }
        }
        _state.update { it.copy(bridge = bridges) }
        calculateResults(itemCount, bridges)
    }

    private fun calculateResults(itemCount: Int, bridges: List<Bridge>) {
        val results = mutableMapOf<Int, Int>()
        for (start in 0 until itemCount) {
            var current = start
            val sortedBridges = bridges.sortedBy { it.step }

            for (step in 1..10) {
                val bridgeRight = sortedBridges.find { it.step == step && it.colIndex == current }
                val bridgeLeft = sortedBridges.find { it.step == step && it.colIndex == current - 1 }

                if (bridgeRight != null) {
                    current += 1
                } else if (bridgeLeft != null) {
                    current -= 1
                }
            }
            results[start] = current
        }
        _state.update { it.copy(gameResult = results) }
    }

    private fun checkAndShowAd() {
        interactionCount++
        if (interactionCount % 3 == 0) {
            viewModelScope.launch {
                _event.emit(MainEvent.ShowAd)
            }
        }
        // Loading ad is a side effect usually managed by the AdManager or triggered here if needed.
        // Assuming AdManager manages loading itself or called appropriately.
        // We could emit an event to load ad if needed, but per request, we use Event for ShowAd.
    }
}