package com.example.easyfood.db


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easyfood.data.db.Dao
import com.example.easyfood.data.db.Repository
import com.example.easyfood.data.pojo.MealDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mockMealDao = mock(Dao::class.java)
    private val repository = Repository(mockMealDao)

    @Test
    fun `getMealList returns meal list from Dao`() {
        // Given
        val mockMealList = MutableLiveData<List<MealDB>>()
        `when`(mockMealDao.getAllSavedMeals()).thenReturn(mockMealList)

        // When
        val mealList: LiveData<List<MealDB>> = repository.mealList

        // Then
        assertEquals(mockMealList, mealList)
        verify(mockMealDao).getAllSavedMeals()
    }

    @Test
    fun `insertFavoriteMeal calls insertFavorite on Dao`() = runTest {
        // Given
        val meal = MealDB("1", "Meal Name", "Category", "Area", "Instructions", "Image URL", "Youtube URL")

        // When
        repository.insertFavoriteMeal(meal)

        // Then
        verify(mockMealDao).insertFavorite(meal)
    }

    @Test
    fun `getMealById returns meal from Dao`() = runTest {
        // Given
        val mealId = "1"
        val mockMeal = MealDB(mealId, "Meal Name", "Category", "Area", "Instructions", "Image URL", "Youtube URL")
        `when`(mockMealDao.getMealById(mealId)).thenReturn(mockMeal)

        // When
        val meal = repository.getMealById(mealId)

        // Then
        assertEquals(mockMeal, meal)
        verify(mockMealDao).getMealById(mealId)
    }

    @Test
    fun `deleteMealById calls deleteMealById on Dao`() = runTest {
        // Given
        val mealId = "1"

        // When
        repository.deleteMealById(mealId)

        // Then
        verify(mockMealDao).deleteMealById(mealId)
    }

    @Test
    fun `deleteMeal calls deleteMeal on Dao`() = runTest {
        // Given
        val meal = MealDB("1", "Meal Name", "Category", "Area", "Instructions", "Image URL", "Youtube URL")

        // When
        repository.deleteMeal(meal)

        // Then
        verify(mockMealDao).deleteMeal(meal)
    }
}
