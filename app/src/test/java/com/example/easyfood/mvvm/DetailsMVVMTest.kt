package com.example.easyfood.mvvm

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.easyfood.data.db.MealsDatabase
import com.example.easyfood.data.db.Repository
import com.example.easyfood.data.pojo.MealDB
import com.example.easyfood.data.pojo.MealDetail
import com.example.easyfood.data.pojo.RandomMealResponse
import com.example.easyfood.data.retrofit.RetrofitInstance
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DetailsMVVMTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var mealsDatabase: MealsDatabase

    @Mock
    private lateinit var observer: Observer<List<MealDetail>>

    @Mock
    private lateinit var call: Call<RandomMealResponse>

    private lateinit var viewModel: DetailsMVVM

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        viewModel = mock(DetailsMVVM::class.java)
    }


    @Test
    fun `getMealById success`() = runTest {
        val mealDetail = listOf(MealDetail("1", "Test Meal", "Test Category", "Test Area", "Test Instructions", "Test Thumbnail", "Test Tags","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s","s"))
        val randomMealResponse = RandomMealResponse(meals = mealDetail)

        `when`(RetrofitInstance.foodApi.getMealById("1")).thenReturn(call)
        val response = Response.success(randomMealResponse)
        doAnswer {
            val callback = it.getArgument<Callback<RandomMealResponse>>(0)
            callback.onResponse(call, response)
        }.`when`(call).enqueue(any())

        viewModel.getMealById("1")

        verify(observer).onChanged(mealDetail)
        Assert.assertEquals(mealDetail, viewModel.mealDetail.value)
    }

    @Test
    fun `getMealById failure`() = runTest {
        val errorMessage = "Error"
        `when`(RetrofitInstance.foodApi.getMealById("1")).thenReturn(call)
        doAnswer {
            val callback = it.getArgument<Callback<RandomMealResponse>>(0)
            callback.onFailure(call, Throwable(errorMessage))
        }.`when`(call).enqueue(any())

        viewModel.getMealById("1")

        verify(observer, never()).onChanged(any())
        Assert.assertNull(viewModel.mealDetail.value)
    }


    @Test
    fun `insertMeal inserts meal into database`() = runTest {
        val meal = MealDB("1", "Test Meal", "Test Category", "Test Area", "Test Instructions", "Test Thumbnail", "Test Tags")

        viewModel.insertMeal(meal)

        runBlocking {
            verify(repository).insertFavoriteMeal(meal)
        }
    }

    @Test
    fun `deleteMeal removes meal from database`() = runTest {
        val meal = MealDB("1", "Test Meal", "Test Category", "Test Area", "Test Instructions", "Test Thumbnail", "Test Tags")

        viewModel.deleteMeal(meal)

        runBlocking {
            verify(repository).deleteMeal(meal)
        }
    }
}
