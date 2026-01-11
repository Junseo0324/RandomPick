package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.repository.PickRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CreatePickListUseCaseTest {

    private val repository: PickRepository = mockk(relaxed = true)
    private val useCase = CreatePickListUseCase(repository)

    @Test
    fun `execute inserts list with correct title`() = runTest {
        // Given
        val title = "New List"
        val slot = slot<PickList>()
        coEvery { repository.insertList(capture(slot)) } returns 1L

        // When
        useCase.execute(title)

        // Then
        coVerify(exactly = 1) { repository.insertList(any()) }
        assertEquals(title, slot.captured.title)
    }
}
