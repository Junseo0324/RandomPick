package com.devhjs.randompick.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.randompick.core.util.AdManager
import com.devhjs.randompick.domain.model.Bridge
import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.usecase.GetPickListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPickListsUseCase: GetPickListsUseCase
) : ViewModel() {

    private val _lists = MutableStateFlow<List<PickList>>(emptyList())
    val lists: StateFlow<List<PickList>> = _lists

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _ladderBridges = MutableStateFlow<List<Bridge>>(emptyList())
    val ladderBridges: StateFlow<List<Bridge>> = _ladderBridges

    private val _gameResult = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val gameResult: StateFlow<Map<Int, Int>> = _gameResult

    fun loadLists() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val result = getPickListsUseCase.execute()
                _lists.value = result
            } catch (e: Exception) {
                _lists.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateLadder(itemCount: Int) {
        if (itemCount < 2) {
            _ladderBridges.value = emptyList()
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
        _ladderBridges.value = bridges
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
        _gameResult.value = results
    }

    private var interactionCount = 0

    fun checkAndShowAd(activity: android.app.Activity) {
        interactionCount++
        if (interactionCount % 3 == 0) {
            AdManager.showInterstitialAd(activity)
        } else {
            // Preload next ad if needed, though AdManager handles it.
            AdManager.loadInterstitialAd(activity)
        }
    }
}