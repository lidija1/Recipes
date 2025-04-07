package com.example.easyfood.fragment
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ImageView
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.lifecycle.MutableLiveData
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.bumptech.glide.Glide
//import com.example.easyfood.R
//import com.example.easyfood.adapters.CategoriesRecyclerAdapter
//import com.example.easyfood.adapters.MostPopularRecyclerAdapter
//import com.example.easyfood.data.pojo.*
//import com.example.easyfood.databinding.FragmentHomeBinding
//import com.example.easyfood.mvvm.DetailsMVVM
//import com.example.easyfood.mvvm.MainFragMVVM
//import com.example.easyfood.ui.activites.MealDetailesActivity
//import com.example.easyfood.ui.fragments.HomeFragment
//import com.google.common.truth.Truth.assertThat
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class HomeFragmentTest {
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var mockDetailsMVVM: DetailsMVVM
//    private lateinit var mockMainFragMVVM: MainFragMVVM
//    private lateinit var mockCategoriesAdapter: CategoriesRecyclerAdapter
//    private lateinit var mockMostPopularAdapter: MostPopularRecyclerAdapter
//    private lateinit var fakeRandomMealResponse: MutableLiveData<RandomMealResponse>
//    private lateinit var fakeMealsResponse: MutableLiveData<MealsResponse>
//    private lateinit var fakeCategoryResponse: MutableLiveData<CategoryResponse>
//
//    @Before
//    fun setup() {
//        mockDetailsMVVM = mockk(relaxed = true)
//        mockMainFragMVVM = mockk(relaxed = true)
//        mockCategoriesAdapter = mockk(relaxed = true)
//        mockMostPopularAdapter = mockk(relaxed = true)
//        fakeRandomMealResponse = MutableLiveData()
//        fakeMealsResponse = MutableLiveData()
//        fakeCategoryResponse = MutableLiveData()
//
//        every { mockMainFragMVVM.observeMealByCategory() } returns fakeMealsResponse
//        every { mockMainFragMVVM.observeCategories() } returns fakeCategoryResponse
//        every { mockMainFragMVVM.observeRandomMeal() } returns fakeRandomMealResponse
//        every { mockDetailsMVVM.observeMealBottomSheet() } returns MutableLiveData()
//    }
//
//    @Test
//    fun testObserveRandomMeal() {
//        val randomMeal = RandomMealResponse(listOf(Meal("1", "Meal 1", "Category 1", "Area 1", "Instructions", "Thumb 1", "Youtube 1", "Tags")))
//        fakeRandomMealResponse.postValue(randomMeal)
//
//        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.Theme_AppCompat) {
//            HomeFragment().apply {
//                detailMvvm = mockDetailsMVVM
//                binding = FragmentHomeBinding.inflate(layoutInflater)
//            }
//        }
//
//        scenario.onFragment { fragment ->
//            val imageView = fragment.view?.findViewById<ImageView>(R.id.img_random_meal)
//            assertThat(imageView).isNotNull()
//            verify { Glide.with(fragment).load(randomMeal.meals[0].strMealThumb).into(imageView!!) }
//            assertThat(fragment.randomMealId).isEqualTo(randomMeal.meals[0].idMeal)
//        }
//    }
//
//    @Test
//    fun testOnRandomMealClick() {
//        val randomMeal = RandomMealResponse(listOf(Meal("1", "Meal 1", "Category 1", "Area 1", "Instructions", "Thumb 1", "Youtube 1", "Tags")))
//        fakeRandomMealResponse.postValue(randomMeal)
//
//        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.Theme_AppCompat) {
//            HomeFragment().apply {
//                detailMvvm = mockDetailsMVVM
//                binding = FragmentHomeBinding.inflate(layoutInflater)
//            }
//        }
//
//        scenario.onFragment { fragment ->
//            fragment.binding.randomMeal.performClick()
//
//            val expectedIntent = Intent(ApplicationProvider.getApplicationContext(), MealDetailesActivity::class.java).apply {
//                putExtra(HomeFragment.MEAL_ID, randomMeal.meals[0].idMeal)
//                putExtra(HomeFragment.MEAL_STR, randomMeal.meals[0].strMeal)
//                putExtra(HomeFragment.MEAL_THUMB, randomMeal.meals[0].strMealThumb)
//            }
//
//            val actualIntent = shadowOf(ApplicationProvider.getApplicationContext()).nextStartedActivity
//            assertThat(actualIntent).isEqualTo(expectedIntent)
//        }
//    }
//
//    @Test
//    fun testCategoryAdapterIsSet() {
//        val categoryResponse = CategoryResponse(listOf(Category("1", "Category 1", "Description", "Thumb 1")))
//        fakeCategoryResponse.postValue(categoryResponse)
//
//        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.Theme_AppCompat) {
//            HomeFragment().apply {
//                binding = FragmentHomeBinding.inflate(layoutInflater)
//                myAdapter = mockCategoriesAdapter
//            }
//        }
//
//        scenario.onFragment { fragment ->
//            verify { mockCategoriesAdapter.setCategoryList(categoryResponse.categories) }
//            assertThat(fragment.binding.recyclerView.adapter).isEqualTo(mockCategoriesAdapter)
//        }
//    }
//
//    @Test
//    fun testMostPopularMealClick() {
//        val meal = Meal("1", "Meal 1", "Category 1", "Area 1", "Instructions", "Thumb 1", "Youtube 1", "Tags")
//
//        every { mockMostPopularAdapter.setOnClickListener(any()) } answers {
//            firstArg<OnItemClick>().onItemClick(meal)
//        }
//
//        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.Theme_AppCompat) {
//            HomeFragment().apply {
//                binding = FragmentHomeBinding.inflate(layoutInflater)
//                mostPopularFoodAdapter = mockMostPopularAdapter
//            }
//        }
//
//        scenario.onFragment { fragment ->
//            verify { mockMostPopularAdapter.setOnClickListener(any()) }
//
//            val expectedIntent = Intent(ApplicationProvider.getApplicationContext(), MealDetailesActivity::class.java).apply {
//                putExtra(HomeFragment.MEAL_ID, meal.idMeal)
//                putExtra(HomeFragment.MEAL_STR, meal.strMeal)
//                putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
//            }
//
//            val actualIntent = shadowOf(ApplicationProvider.getApplicationContext()).nextStartedActivity
//            assertThat(actualIntent).isEqualTo(expectedIntent)
//        }
//    }
//}
