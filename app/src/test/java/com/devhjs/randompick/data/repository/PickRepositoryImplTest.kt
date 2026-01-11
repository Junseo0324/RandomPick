package com.devhjs.randompick.data.repository

import com.devhjs.randompick.data.local.dao.PickDao
import com.devhjs.randompick.data.local.entity.PickItemEntity
import com.devhjs.randompick.data.local.entity.PickListEntity
import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.model.PickList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PickRepositoryImplTest {

    private val dao: PickDao = mockk(relaxed = true)
    private val repository = PickRepositoryImpl(dao)

    @Test
    fun `getLists returns mapped models`() = runTest {
        // Given
        val listEntity = PickListEntity(id = 1, title = "List 1", createdAt = 100L)
        val itemEntity = PickItemEntity(id = 1, listId = 1, name = "Item 1", createdAt = 200L)
        
        coEvery { dao.getLists() } returns listOf(listEntity)
        coEvery { dao.getItemsByListId(1) } returns listOf(itemEntity)

        // When
        val result = repository.getLists()

        // Then
        assertEquals(1, result.size)
        assertEquals("List 1", result[0].title)
        assertEquals(1, result[0].items.size)
        assertEquals("Item 1", result[0].items[0].name)
    }

    @Test
    fun `insertList inserts entity and returns id`() = runTest {
        // Given
        val simpleList = PickList(title = "New List")
        val entitySlot = slot<PickListEntity>()
        coEvery { dao.insertList(capture(entitySlot)) } returns 10L

        // When
        val result = repository.insertList(simpleList)

        // Then
        assertEquals(10L, result)
        assertEquals("New List", entitySlot.captured.title)
        coVerify { dao.insertList(any()) }
    }



    @Test
    fun `deleteList deletes items and the list`() = runTest {
        // Given
        val pickList = PickList(id = 5, title = "To Delete")

        // When
        repository.deleteList(pickList)

        // Then
        coVerify(ordering = io.mockk.Ordering.ORDERED) {
            dao.deleteItemsByListId(5)
            dao.deleteList(any())
        }
    }

    @Test
    fun `insertItem inserts when count is less than MAX_ITEMS`() = runTest {
        // Given
        val item = PickItem(listId = 1, name = "Item")
        coEvery { dao.getItemCountByListId(1) } returns 5 // 5 < 10

        // When
        val result = repository.insertItem(item)

        // Then
        assertTrue(result)
        coVerify { dao.insertItem(any()) }
    }

    @Test
    fun `insertItem fails when count is equal or greater than MAX_ITEMS`() = runTest {
        // Given
        val item = PickItem(listId = 2, name = "Item")
        coEvery { dao.getItemCountByListId(2) } returns 10 // MAX_ITEMS = 10

        // When
        val result = repository.insertItem(item)

        // Then
        assertFalse(result)
        coVerify(exactly = 0) { dao.insertItem(any()) }
    }
}
