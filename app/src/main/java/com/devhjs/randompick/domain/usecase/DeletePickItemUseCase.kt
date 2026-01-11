package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.repository.PickRepository
import javax.inject.Inject

class DeletePickItemUseCase @Inject constructor(
    private val repository: PickRepository
) {
    suspend fun execute(item: PickItem) {
        repository.deleteItem(item)
    }
}
