package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.repository.PickRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPickListsUseCaseTest {

    private val repository: PickRepository = mockk()
    private val useCase = GetPickListsUseCase(repository)

    @Test
    fun `execute returns list from repository`() = runTest {
        // Given
        val expectedList = listOf(
            PickList(id = 1, title = "List 1"),
            PickList(id = 2, title = "List 2")
        )
        coEvery { repository.getLists() } returns expectedList

        // When
        val result = useCase.execute()

        // Then
        assertEquals(expectedList, result)
        coVerify(exactly = 1) { repository.getLists() }
    }
}
