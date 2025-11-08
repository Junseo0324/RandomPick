package com.devhjs.randompick.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.randompick.core.data.local.entity.PickListEntity
import com.devhjs.randompick.core.data.repository.PickRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: PickRepository
) : ViewModel() {

    private val _lists = MutableStateFlow<List<PickListEntity>>(emptyList())
    val lists: StateFlow<List<PickListEntity>> = _lists

    fun loadLists() {
        viewModelScope.launch {
            _lists.value = repository.getLists()
        }
    }

    fun addList(title: String) {
        viewModelScope.launch {
            repository.insertList(PickListEntity(title = title))
            loadLists()
        }
    }
}