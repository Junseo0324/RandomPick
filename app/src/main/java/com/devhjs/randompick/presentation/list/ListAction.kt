package com.devhjs.randompick.presentation.list

import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.model.PickList

sealed interface ListAction {
    data object OnAddListClick : ListAction
    data class OnAddListConfirm(val title: String) : ListAction
    data object OnAddListDismiss : ListAction
    
    data class OnListClick(val list: PickList) : ListAction
    data object OnEditListDismiss : ListAction
    
    data class OnDeleteList(val list: PickList) : ListAction
    data class OnUpdateList(val list: PickList) : ListAction
    data class OnAddItem(val listId: Int, val name: String) : ListAction
    data class OnDeleteItem(val item: PickItem) : ListAction
    data class OnShowError(val message: String) : ListAction
}
