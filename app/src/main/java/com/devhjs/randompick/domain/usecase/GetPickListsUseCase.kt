package com.devhjs.randompick.domain.usecase

import kotlinx.coroutines.flow.Flow
import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.repository.PickRepository
import javax.inject.Inject

class GetPickListsUseCase @Inject constructor(
    private val repository: PickRepository
) {
    fun execute(): Flow<List<PickList>> {
        return repository.getLists()
    }
}
