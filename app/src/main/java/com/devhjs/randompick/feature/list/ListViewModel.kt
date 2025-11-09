package com.devhjs.randompick.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.randompick.core.data.repository.PickRepository
import com.devhjs.randompick.core.model.PickItem
import com.devhjs.randompick.core.model.PickList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: PickRepository
) : ViewModel() {

    private val _lists = MutableStateFlow<List<PickList>>(emptyList())
    val lists: StateFlow<List<PickList>> = _lists

    fun loadLists() {
        viewModelScope.launch {
            _lists.value = repository.getLists()
        }
    }

    fun addList(title: String) {
        viewModelScope.launch {
            val newList = PickList(
                title = title,
                items = emptyList()
            )
            repository.insertList(newList)
            loadLists()
        }
    }

    fun updateList(updatedList: PickList) {
        viewModelScope.launch {
            repository.updateList(updatedList)
            loadLists()
        }
    }

    fun deleteList(list: PickList) {
        viewModelScope.launch {
            repository.deleteList(list)
            loadLists()
        }
    }

    fun addItem(listId: Int, name: String) {
        viewModelScope.launch {
            val newItem = PickItem(listId = listId, name = name)
            repository.insertItem(newItem)
            loadLists()
        }
    }

    fun updateItem(item: PickItem) {
        viewModelScope.launch {
            repository.updateItem(item)
            loadLists()
        }
    }

    fun deleteItem(item: PickItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
            loadLists()
        }
    }
}