package com.example.easyfood.mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.data.pojo.CategoryResponse
import com.example.easyfood.data.retrofit.FoodApi
import org.mockito.ArgumentMatchers.any
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMVVMTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var foodApi: FoodApi

    @Mock
    private lateinit var mockCall: Call<CategoryResponse>

    @Mock
    private lateinit var observer: Observer<List<Category>>

    private lateinit var categoryMVVM: CategoryMVVM

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        // Mock the API call to return the mockCall object
        `when`(foodApi.getCategories()).thenReturn(mockCall)
        categoryMVVM = CategoryMVVM(foodApi)
    }

    @Test
    fun testGetCategoriesSuccess() {
        val categories = listOf(
            Category(
                idCategory = "1",
                strCategory = "Beef",
                strCategoryThumb = "https://www.themealdb.com/images/category/beef.png",
                strCategoryDescription = "Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times. Beef is a source of high-quality protein and essential nutrients."
            )
        )
        val categoryResponse = CategoryResponse(categories)

        doAnswer { invocation ->
            val callback: Callback<CategoryResponse> = invocation.getArgument(0)
            callback.onResponse(mockCall, Response.success(categoryResponse))
            null
        }.`when`(mockCall).enqueue(any())

        categoryMVVM.observeCategories().observeForever(observer)

        verify(observer).onChanged(categories)
    }

    @Test
    fun testGetCategoriesFailure() {
        val errorMessage = "Network Error"

        doAnswer { invocation ->
            val callback: Callback<CategoryResponse> = invocation.getArgument(0)
            callback.onFailure(mockCall, Throwable("Network Error"))
            null
        }.`when`(mockCall).enqueue(any<Callback<CategoryResponse>>())


        categoryMVVM.observeCategories().observeForever(observer)

        // Ensure you provide a non-null verification if needed
        verify(observer, never()).onChanged(any())
    }

}
