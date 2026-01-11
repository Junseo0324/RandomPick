package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.repository.PickRepository
import javax.inject.Inject

class CreatePickItemUseCase @Inject constructor(
    private val repository: PickRepository
) {
    suspend fun execute(listId: Int, name: String): Boolean {
        val newItem = PickItem(listId = listId, name = name)
        return repository.insertItem(newItem)
    }
}
