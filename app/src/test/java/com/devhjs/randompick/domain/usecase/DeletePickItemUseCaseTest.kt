package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.repository.PickRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeletePickItemUseCaseTest {

    private val repository: PickRepository = mockk(relaxed = true)
    private val useCase = DeletePickItemUseCase(repository)

    @Test
    fun `execute deletes item from repository`() = runTest {
        // Given
        val item = PickItem(id = 1, listId = 1, name = "Item 1")

        // When
        useCase.execute(item)

        // Then
        coVerify(exactly = 1) { repository.deleteItem(item) }
    }
}
