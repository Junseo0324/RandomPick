package com.devhjs.randompick.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devhjs.randompick.core.data.repository.PickRepository
import com.devhjs.randompick.core.model.PickList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ListViewModel
    private val repository: PickRepository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadLists updates lists from repository`() = runTest(testDispatcher) {
        // Given
        val mockLists = listOf(PickList(id = 1, title = "Test List", items = emptyList()))
        coEvery { repository.getLists() } returns mockLists

        // When
        viewModel.loadLists()
        advanceUntilIdle()

        // Then
        assertEquals(mockLists, viewModel.lists.value)
    }

    @Test
    fun `addList inserts list and reloads`() = runTest(testDispatcher) {
        // Given
        val title = "New List"

        // When
        viewModel.addList(title)
        advanceUntilIdle()

        // Then
        coVerify { repository.insertList(any()) }
        coVerify { repository.getLists() }
    }

    @Test
    fun `addItem emits error when repository returns false`() = runTest(testDispatcher) {
        // Given
        val listId = 1
        val name = "Item 1"
        coEvery { repository.insertItem(any()) } returns false

        // When
        viewModel.addItem(listId, name)
        advanceUntilIdle()
        
        coVerify { repository.insertItem(any()) }
    }
}
