package com.example.easyfood.ui.fragments

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.mvvm.CategoryMVVM
//import com.example.easyfood.adapters.CategoriesRecyclerAdapter
import com.example.easyfood.databinding.FragmentCategoryBinding
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

//@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
@RunWith(AndroidJUnit4::class)
class CategoryFragmentTest {

    @Test
    fun testObserveCategories() {
        // Arrange
        val mockMvvm = mock(CategoryMVVM::class.java)
        val liveData = MutableLiveData<List<Category>>()
        `when`(mockMvvm.observeCategories()).thenReturn(liveData)

        val scenario = launchFragmentInContainer<CategoryFragment>()
        scenario.onFragment { fragment ->
            fragment.injectCategoryMvvm(mockMvvm)
        }

        // Act
        val testCategories = listOf(Category("1","Beef","https:\\/\\/www.themealdb.com\\/images\\/category\\/beef.png","Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]"))
        liveData.postValue(testCategories)

        // Assert
        scenario.onFragment { fragment ->
            val adapter = fragment.getMyAdapter()
            assertEquals(testCategories, adapter.getCategoryList())
        }
    }

    @Test
    fun testRecyclerViewSetup() {
        val scenario = launchFragmentInContainer<CategoryFragment>()
        scenario.onFragment { fragment ->
            val bindingField = CategoryFragment::class.java.getDeclaredField("binding")
            bindingField.isAccessible = true
            val binding = bindingField.get(fragment) as FragmentCategoryBinding

            val recyclerView = binding.favoriteRecyclerView
            assertNotNull(recyclerView)
            assertEquals(3, (recyclerView.layoutManager as GridLayoutManager).spanCount)
        }
    }
}




//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Observer
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.easyfood.data.pojo.Category
//import com.example.easyfood.mvvm.CategoryMVVM
//import com.example.easyfood.ui.fragments.CategoryFragment
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import kotlin.test.assertEquals
//
////@RunWith(AndroidJUnit4::class)
//@RunWith(JUnit4::class)
//class CategoryFragmentTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var categoryMVVM: CategoryMVVM
//
//    @Before
//    fun setUp() {
//        categoryMVVM = mockk(relaxed = true)
//    }
//
//    @Test
//    fun testGetCategoryName() {
//        val testCategory = Category(
//            "1",
//            "Beef",
//            "Beef is the culinary name for meat from cattle, particularly skeletal muscle.",
//            "https://www.themealdb.com/images/category/beef.png"
//        )
//        val categories = listOf(testCategory)
//
//        val liveData = MutableLiveData<List<Category>>()
//        liveData.value = categories
//        every { categoryMVVM.observeCategories() } returns liveData
//
//        val scenario = launchFragmentInContainer<CategoryFragment>()
//        scenario.onFragment { fragment ->
//            fragment.injectCategoryMvvm(categoryMVVM)
//            fragment.observeCategories()
//
//            verify { categoryMVVM.observeCategories() }
//
//            assertEquals(categories, fragment.getMyAdapter().getCategoryList())
//        }
//    }
//}


//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.example.easyfood.data.pojo.Category
//import com.example.easyfood.mvvm.CategoryMVVM
//import com.example.easyfood.ui.fragments.CategoryFragment
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mockito.*
//import kotlin.test.assertEquals
//
//class CategoryFragmentTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var categoryFragment: CategoryFragment
//    private lateinit var categoryMVVM: CategoryMVVM
//
//    @Before
//    fun setUp() {
//        categoryMVVM = mock(CategoryMVVM::class.java)
//        categoryFragment = CategoryFragment().apply {
//            categoryMvvm = categoryMVVM  // Postavljanje mock ViewModel-a
//        }
//    }
//
//    @Test
//    fun testGetCategoryName() {
//        // Pripremi testne podatke
//        val testCategory = Category("1","Beef","Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]","https:\\/\\/www.themealdb.com\\/images\\/category\\/beef.png")
//        val categories = listOf(testCategory)
//        `when`(categoryMVVM.observeCategories().value).thenReturn(categories)
//
//        // Pokreni metodu koja koristi observeCategories
//        categoryFragment.observeCategories()
//
//        // Verifikuj da je metoda pozvana
//        verify(categoryMVVM).observeCategories()
//
//        // Proveri da li je adapter postavljen sa očekivanim podacima
//        val expectedCategoryList = listOf(testCategory)
//        assertEquals(expectedCategoryList, categoryFragment.getMyAdapter().getCategoryList())
//    }
//}


//import com.example.easyfood.ui.fragments.CategoryFragment
//import org.junit.Assert.assertEquals
//import org.junit.Test
//
//class CategoryFragmentTest {
//
//    @Test
//    fun testGetCategoryName() {
//        val fragment = CategoryFragment()
//        val expectedName = "Beef"
//        val actualName = fragment.getCategoryMvvm()
//
//        assertEquals(expectedName, actualName)
//    }
//}
//
////package com.example.easyfood.fragment
////import androidx.fragment.app.testing.FragmentScenario
////import androidx.fragment.app.testing.launchFragmentInContainer
////import androidx.lifecycle.MutableLiveData
////import androidx.recyclerview.widget.GridLayoutManager
////import androidx.test.ext.junit.runners.AndroidJUnit4
////import com.example.easyfood.adapters.CategoriesRecyclerAdapter
////import com.example.easyfood.data.pojo.Category
////import com.example.easyfood.databinding.FragmentCategoryBinding
////import com.example.easyfood.mvvm.CategoryMVVM
////import com.example.easyfood.ui.fragments.CategoryFragment
////import com.example.easyfood.R
////import org.junit.Assert.*
////import org.junit.Before
////import org.junit.Test
////import org.junit.runner.RunWith
////import org.mockito.Mockito.*
////
////@RunWith(AndroidJUnit4::class)
////class CategoryFragmentTest {
////
////    private lateinit var scenario: FragmentScenario<CategoryFragment>
////    private lateinit var mockMvvm: CategoryMVVM
////    private lateinit var mockAdapter: CategoriesRecyclerAdapter
//////    private lateinit var mockBinding: FragmentCategoryBinding
//// private val mockCategories = MutableLiveData<List<Category>>()
////
////    @Before
////    fun setup() {
////        // Pokretanje CategoryFragment-a
////        scenario = launchFragmentInContainer(themeResId = R.style.Theme_AppCompat)
////
////        // Kreiraj mockove
////        mockMvvm = mock(CategoryMVVM::class.java)
////        mockAdapter = mock(CategoriesRecyclerAdapter::class.java)
////
////        // Postavi mock da vraća MutableLiveData
////        `when`(mockMvvm.observeCategories()).thenReturn(mockCategories)
////
////        // Injektuj mockove u fragment koristeći getter metode
////        scenario.onFragment { fragment ->
////            fragment.getCategoryMvvm().observeCategories().value = mockCategories.value
////            fragment.getMyAdapter().setCategoryList(emptyList())
////        }
////    }
//////    @Test
//////    fun testObserveCategories() {
//////        scenario.onFragment { fragment ->
//////            // Postavi vrednost za MutableLiveData
//////            val categories = listOf(Category("Test Category"))
//////            mockCategories.postValue(categories)
//////
//////            // Poziv privatne metode preko companion object-a
//////            CategoryFragment.callObserveCategories(fragment)
//////
//////            verify(mockAdapter).setCategoryList(categories)
//////        }
//////    }
////
////    @Test
////    fun testPrepareRecyclerView() {
////        scenario.onFragment { fragment ->
////            // Poziv privatne metode preko companion object-a
////            CategoryFragment.callPrepareRecyclerView(fragment)
////
////            val layoutManager = fragment.getBinding().favoriteRecyclerView.layoutManager
////            assertNotNull(layoutManager)
////            assertTrue(layoutManager is GridLayoutManager)
////            assertEquals(3, (layoutManager as GridLayoutManager).spanCount)
////        }
////    }
////
////    @Test
////    fun testObserveCategories() {
////        scenario.onFragment { fragment ->
////            val mockCategories = listOf(Category("Test Category"))
////            val liveData = MutableLiveData<List<Category>>().apply {
////                value = mockCategories
////            }
////
////            `when`(mockMvvm.observeCategories()).thenReturn(liveData)
////
////            // Poziv privatne metode preko companion object-a
////            CategoryFragment.callObserveCategories(fragment)
////
////            verify(mockAdapter).setCategoryList(mockCategories)
////        }
////    }
////
////    @Test
////    fun testOnCategoryClick() {
////        scenario.onFragment { fragment ->
////            // Poziv privatne metode preko companion object-a
////            CategoryFragment.callOnCategoryClick(fragment)
////
////            // Ovim bi trebalo da se testira da li je klik akcija ispravno postavljena
////            fragment.getMyAdapter().onItemClicked(object : CategoriesRecyclerAdapter.OnItemCategoryClicked {
////                override fun onClickListener(category: Category) {
////                    assertNotNull(category)
////                    assertEquals("Test Category", category.strCategory)
////                }
////            })
////        }
////    }
////}
