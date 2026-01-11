package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.repository.PickRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeletePickListUseCaseTest {

    private val repository: PickRepository = mockk(relaxed = true)
    private val useCase = DeletePickListUseCase(repository)

    @Test
    fun `execute deletes list from repository`() = runTest {
        // Given
        val list = PickList(id = 1, title = "Delete List")

        // When
        useCase.execute(list)

        // Then
        coVerify(exactly = 1) { repository.deleteList(list) }
    }
}
