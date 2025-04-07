package com.example.easyfood.api

import com.example.easyfood.data.pojo.Category
import com.example.easyfood.data.pojo.MealsResponse
import com.example.easyfood.data.pojo.Meal
import com.example.easyfood.data.pojo.CategoryResponse
import com.example.easyfood.data.retrofit.FoodApi
import junit.framework.TestCase.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var foodApi: FoodApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        foodApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Koristimo baznu URL iz MockWebServer-a
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCategories returns expected response`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"categories":[{"idCategory":"1","strCategory":"Beef","strCategoryThumb":"https://www.themealdb.com/images/category/beef.png","strCategoryDescription":"Category Description"}]}""")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = foodApi.getCategories().execute()

        // Then
        val expected = CategoryResponse(
            categories = listOf(
                Category(
                    idCategory = "1",
                    strCategory = "Beef",
                    strCategoryThumb = "https://www.themealdb.com/images/category/beef.png",
                    strCategoryDescription = "Category Description"
                )
            )
        )
        assertEquals(expected, response.body())
    }

    @Test
    fun `getMealsByCategory returns expected response`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"meals":[{"strMeal":"Beef and Mustard Pie","strMealThumb":"https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg","idMeal":"52874"}]}""")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = foodApi.getMealsByCategory("Beef").execute()

        // Then
        val expected = MealsResponse(
            meals = listOf(
                Meal(
                    strMeal = "Beef and Mustard Pie",
                    strMealThumb = "https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg",
                    idMeal = "52874"
                )
            )
        )
        assertEquals(expected, response.body())
    }
}

//package com.example.easyfood.api
//
//import com.example.easyfood.data.pojo.Category
//import com.example.easyfood.data.pojo.MealsResponse
//import com.example.easyfood.data.pojo.Meal
//import com.example.easyfood.data.pojo.RandomMealResponse
//import com.example.easyfood.data.pojo.CategoryResponse
//import com.example.easyfood.data.pojo.MealDetail
//import com.example.easyfood.data.retrofit.FoodApi
//import com.google.gson.Gson
//import junit.framework.TestCase.assertEquals
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
////import kotlin.test.assertEquals
//
//class FoodApiTest {
//
//    private lateinit var mockWebServer: MockWebServer
//    private lateinit var foodApi: FoodApi
//
//    @Before
//    fun setUp() {
//        mockWebServer = MockWebServer()
//        foodApi = Retrofit.Builder()
//            .baseUrl(mockWebServer.url("https://www.themealdb.com/api/json/v1/1/")) // Set the base URL to the MockWebServer's URL
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(FoodApi::class.java)
//    }
//
//    @After
//    fun tearDown() {
//        mockWebServer.shutdown()
//    }
//
//    @Test
//    fun `getCategories returns expected response`() {
//        // Given
//        val mockResponse = MockResponse()
//            .setResponseCode(200)
//            .setBody("""{"categories":[{"idCategory":"1","strCategory":"Beef","strCategoryThumb":"https://www.themealdb.com/images/category/beef.png","strCategoryDescription":"Category Description"}]}""")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = foodApi.getCategories().execute()
//
//        // Then
//        val expected = CategoryResponse(
//            categories = listOf(
//                Category(
//                    idCategory = "1",
//                    strCategory = "Beef",
//                    strCategoryThumb = "https://www.themealdb.com/images/category/beef.png",
//                    strCategoryDescription = "Category Description"
//                )
//            )
//        )
//        assertEquals(expected, response.body())
//    }
//
//    @Test
//    fun `getMealsByCategory returns expected response`() {
//        // Given
//        val mockResponse = MockResponse()
//            .setResponseCode(200)
//            .setBody("""{"meals":[{"strMeal":"Beef and Mustard Pie","strMealThumb":"https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg","idMeal":"52874"}]}""")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = foodApi.getMealsByCategory("Beef").execute()
//
//        // Then
//        val expectedMealResponse = MealsResponse(
//            meals = listOf(
//                Meal(
//                    strMeal = "Beef and Mustard Pie",
//                    strMealThumb = "https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg",
//                    idMeal = "52874"
//                )
//            )
//        )
//        assertEquals(expected, response.body())
//    }

//    @Test
//    fun `getRandomMeal returns expected response`() {
//        // Given
//        val mockResponse = MockResponse()
//            .setResponseCode(200)
//            .setBody("""{"meals":[{"idMeal":"52772","strMeal":"Teriyaki Chicken Casserole","strCategory":"Chicken","strArea":"Japanese","strInstructions":"Instructions","strMealThumb":"https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg","strYoutube":"https://www.youtube.com/watch?v=4aZr5hZXP_s"}]}""")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = foodApi.getRandomMeal().execute()
//
//        // Then
//        val expectedRandomMealResponse = RandomMealResponse(
//            meals = listOf(
//                MealDetail(
//                    idMeal = "52772",
//                    strMeal = "Teriyaki Chicken Casserole",
//                    strCategory = "Chicken",
//                    strArea = "Japanese",
//                    strInstructions = "Instructions",
//                    strMealThumb = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
//                    strYoutube = "https://www.youtube.com/watch?v=4aZr5hZXP_s"
//                )
//            )
//        )
//        assertEquals(expected, response.body())
//    }
//}
