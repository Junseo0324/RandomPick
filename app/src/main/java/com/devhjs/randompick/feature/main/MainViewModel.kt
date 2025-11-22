package com.devhjs.randompick.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.randompick.core.data.repository.PickRepository
import com.devhjs.randompick.core.model.PickList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PickRepository
) : ViewModel() {

    private val _lists = MutableStateFlow<List<PickList>>(emptyList())
    val lists: StateFlow<List<PickList>> = _lists

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadLists() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val result = repository.getLists()
                _lists.value = result
            } catch (e: Exception) {
                _lists.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}