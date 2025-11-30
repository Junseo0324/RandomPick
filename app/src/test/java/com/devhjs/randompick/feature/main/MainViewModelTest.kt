package com.devhjs.randompick.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devhjs.randompick.core.data.repository.PickRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val repository: PickRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `generateLadder creates bridges for valid item count`() {
        // Given
        val itemCount = 4

        // When
        viewModel.generateLadder(itemCount)

        // Then
        val bridges = viewModel.ladderBridges.value
        bridges.forEach { bridge ->
            assertTrue(bridge.colIndex >= 0)
            assertTrue(bridge.colIndex < itemCount - 1)
            assertTrue(bridge.step > 0)
            assertTrue(bridge.step < 10)
        }
    }

    @Test
    fun `generateLadder clears bridges for less than 2 items`() {
        // Given
        val itemCount = 1

        // When
        viewModel.generateLadder(itemCount)

        // Then
        assertTrue(viewModel.ladderBridges.value.isEmpty())
    }

    @Test
    fun `calculateResults traces path correctly`() {

        val itemCount = 4
        viewModel.generateLadder(itemCount)
        
        val results = viewModel.gameResult.value
        assertEquals(itemCount, results.size)
        
        for (i in 0 until itemCount) {
            assertTrue(results.containsKey(i))
        }
        
        val endIndices = results.values.toSet()
        assertEquals(itemCount, endIndices.size)
        endIndices.forEach { end ->
            assertTrue(end >= 0)
            assertTrue(end < itemCount)
        }
    }
}
