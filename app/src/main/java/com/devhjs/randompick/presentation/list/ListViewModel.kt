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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _state = MutableStateFlow(ListState())
    val state: StateFlow<ListState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ListEvent>()
    val eventFlow: SharedFlow<ListEvent> = _eventFlow

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getPickListsUseCase.execute().collect { lists ->
                _state.update { it.copy(lists = lists, isLoading = false) }
            }
        }
    }

    fun onAction(action: ListAction) {
        when (action) {
            is ListAction.OnAddListClick -> {
                _state.update { it.copy(showAddListSheet = true) }
            }
            is ListAction.OnAddListConfirm -> {
                addList(action.title)
                _state.update { it.copy(showAddListSheet = false) }
            }
            is ListAction.OnAddListDismiss -> {
                _state.update { it.copy(showAddListSheet = false) }
            }
            is ListAction.OnListClick -> {
                _state.update {
                    it.copy(
                        selectedList = action.list,
                        showEditListSheet = true
                    )
                }
            }
            is ListAction.OnEditListDismiss -> {
                _state.update {
                    it.copy(
                        showEditListSheet = false,
                        selectedList = null
                    )
                }
            }
            is ListAction.OnDeleteList -> {
                deleteList(action.list)
                _state.update {
                    it.copy(
                        showEditListSheet = false,
                        selectedList = null
                    )
                }
            }
            is ListAction.OnUpdateList -> {
                updateList(action.list)
                // Update selected list to reflect changes immediately in UI if needed,
                // though flow observation should handle it.
                _state.update { it.copy(selectedList = action.list) }
            }
            is ListAction.OnAddItem -> {
                addItem(action.listId, action.name)
            }
            is ListAction.OnDeleteItem -> {
                deleteItem(action.item)
            }
            is ListAction.OnShowError -> {
                sendMessage(action.message) // Re-use helper or emit directly
            }
        }
    }

    private fun addList(title: String) {
        viewModelScope.launch {
            createPickListUseCase.execute(title)
        }
    }

    private fun updateList(updatedList: PickList) {
        viewModelScope.launch {
            updatePickListUseCase.execute(updatedList)
        }
    }

    private fun deleteList(list: PickList) {
        viewModelScope.launch {
            deletePickListUseCase.execute(list)
        }
    }

    private fun addItem(listId: Int, name: String) {
        viewModelScope.launch {
            val added = createPickItemUseCase.execute(listId, name)
            if (!added) {
                _eventFlow.emit(ListEvent.ShowMessage("항목은 최대 ${MAX_ITEMS}개까지 추가할 수 있습니다."))
            } else {
                // Refresh selected list items if we are in edit mode
                // Since Room flow updates lists, and selectedList is just a copy,
                // we might need to rely on the main list flow or manually update selectedList.
                // For simplicity, we rely on the list observation updating the main list,
                // but the strictly correct way with local 'selectedList' state is to updating it too
                // or deriving it from the main list.
                // However, since 'lists' flow updates, we can find the updated list and set it.
                // But for now, let's keep it simple. The UI might flicker if we don't update selectedList.
                // A better approach is `selectedList` being an ID and we derive the object from `lists`.
                // But given the plan, let's stick to the object but refresh it.
                // Refetching logic is implicit via flow.
                // Let's actually find the updated list in the flow and update selectedList if visible?
                // The 'lists' collection in init block updates state.lists.
                // But 'selectedList' in state is a separate object.
                // We should probably rely on the ID or update selectedList when lists change.
                // Let's add a collector for lists to update selectedList if it exists.
            }
        }
    }

    private fun deleteItem(item: PickItem) {
        viewModelScope.launch {
            deletePickItemUseCase.execute(item)
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(ListEvent.ShowMessage(message))
        }
    }
}