package com.devhjs.randompick.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.randompick.data.repository.MAX_ITEMS
import com.devhjs.randompick.domain.usecase.CreatePickItemUseCase
import com.devhjs.randompick.domain.usecase.CreatePickListUseCase
import com.devhjs.randompick.domain.usecase.DeletePickItemUseCase
import com.devhjs.randompick.domain.usecase.DeletePickListUseCase
import com.devhjs.randompick.domain.usecase.GetPickListsUseCase
import com.devhjs.randompick.domain.usecase.UpdatePickListUseCase
import com.devhjs.randompick.domain.model.ListEvent
import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.model.PickList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getPickListsUseCase: GetPickListsUseCase,
    private val createPickListUseCase: CreatePickListUseCase,
    private val updatePickListUseCase: UpdatePickListUseCase,
    private val deletePickListUseCase: DeletePickListUseCase,
    private val createPickItemUseCase: CreatePickItemUseCase,
    private val deletePickItemUseCase: DeletePickItemUseCase
) : ViewModel() {

    private val _lists = MutableStateFlow<List<PickList>>(emptyList())
    val lists: StateFlow<List<PickList>> = _lists

    private val _eventFlow = MutableSharedFlow<ListEvent>()
    val eventFlow: SharedFlow<ListEvent> = _eventFlow

    fun loadLists() {
        viewModelScope.launch {
            _lists.value = getPickListsUseCase.execute()
        }
    }

    fun addList(title: String) {
        viewModelScope.launch {
            createPickListUseCase.execute(title)
            loadLists()
        }
    }

    fun updateList(updatedList: PickList) {
        viewModelScope.launch {
            updatePickListUseCase.execute(updatedList)
            loadLists()
        }
    }

    fun deleteList(list: PickList) {
        viewModelScope.launch {
            deletePickListUseCase.execute(list)
            loadLists()
        }
    }

    fun addItem(listId: Int, name: String) {
        viewModelScope.launch {
            val added = createPickItemUseCase.execute(listId, name)
            if (!added) {
                _eventFlow.emit(ListEvent.ShowMessage("항목은 최대 ${MAX_ITEMS}개까지 추가할 수 있습니다."))
            }
            loadLists()
        }
    }


    fun deleteItem(item: PickItem) {
        viewModelScope.launch {
            deletePickItemUseCase.execute(item)
            loadLists()
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(ListEvent.ShowMessage(message))
        }
    }
}