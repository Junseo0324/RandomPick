package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.repository.PickRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdatePickListUseCaseTest {

    private val repository: PickRepository = mockk(relaxed = true)
    private val useCase = UpdatePickListUseCase(repository)

    @Test
    fun `execute updates list in repository`() = runTest {
        // Given
        val list = PickList(id = 1, title = "Updated List")

        // When
        useCase.execute(list)

        // Then
        coVerify(exactly = 1) { repository.updateList(list) }
    }
}
