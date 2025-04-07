import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.easyfood.adapters.FavoriteMealsRecyclerAdapter
import com.example.easyfood.data.pojo.MealDB
import com.example.easyfood.mvvm.DetailsMVVM
import com.example.easyfood.ui.FavoriteMeals
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(AndroidJUnit4::class)
class FavoriteMealsTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var detailsMVVM: DetailsMVVM

    @Mock
    private lateinit var adapter: FavoriteMealsRecyclerAdapter

    private lateinit var fragment: FavoriteMeals
    private val favoriteMealsLiveData = MutableLiveData<List<MealDB>>()

    @Before
    fun setUp() {
        fragment = FavoriteMeals()
//        fragment.detailsMVVM = detailsMVVM
//        fragment.myAdapter = adapter

        `when`(detailsMVVM.observeSaveMeal()).thenReturn(favoriteMealsLiveData)
    }

    @Test
    fun testObserveSaveMealUpdatesRecyclerView() {
        // Arrange
        val mealsList = listOf(
            MealDB("1", "Meal1", "Thumb1", "Category1", "Area1","s","s"),
            MealDB("2", "Meal2", "Thumb2", "Category2", "Area2","s","s")
        )

        // Act
        favoriteMealsLiveData.postValue(mealsList)

        // Assert
        verify(adapter).setFavoriteMealsList(mealsList)
        assertThat(fragment.fBinding.tvFavEmpty.visibility).isEqualTo(View.GONE)
    }

    @Test
    fun testEmptyFavoriteMealsListShowsEmptyTextView() {
        // Act
        favoriteMealsLiveData.postValue(emptyList())

        // Assert
        assertThat(fragment.fBinding.tvFavEmpty.visibility).isEqualTo(View.VISIBLE)
    }


}
