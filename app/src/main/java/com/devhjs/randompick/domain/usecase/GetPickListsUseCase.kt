package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.repository.PickRepository
import javax.inject.Inject

class GetPickListsUseCase @Inject constructor(
    private val repository: PickRepository
) {
    suspend fun execute(): List<PickList> {
        return repository.getLists()
    }
}
