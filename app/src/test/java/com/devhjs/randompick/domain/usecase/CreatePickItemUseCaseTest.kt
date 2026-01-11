package com.devhjs.randompick.domain.usecase

import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.repository.PickRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CreatePickItemUseCaseTest {

    private val repository: PickRepository = mockk(relaxed = true)
    private val useCase = CreatePickItemUseCase(repository)

    @Test
    fun `execute inserts item and returns true when successful`() = runTest {
        // Given
        val listId = 1
        val name = "Item 1"
        val slot = slot<PickItem>()
        coEvery { repository.insertItem(capture(slot)) } returns true

        // When
        val result = useCase.execute(listId, name)

        // Then
        assertTrue(result)
        coVerify(exactly = 1) { repository.insertItem(any()) }
        assertEquals(listId, slot.captured.listId)
        assertEquals(name, slot.captured.name)
    }

    @Test
    fun `execute returns false when repository fails`() = runTest {
        // Given
        coEvery { repository.insertItem(any()) } returns false

        // When
        val result = useCase.execute(1, "Item 1")

        // Then
        assertFalse(result)
    }
}
