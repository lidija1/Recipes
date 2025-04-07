package com.example.easyfood.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.example.easyfood.data.pojo.MealDB
import kotlinx.coroutines.runBlocking
import org.junit.*
import java.io.IOException

class DaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MealsDatabase
    private lateinit var dao: Dao

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(
            androidx.test.core.app.ApplicationProvider.getApplicationContext(),
            MealsDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertFavorite_insertsDataCorrectly() = runBlocking {
        val meal = MealDB(
            mealId = "52768",
            mealName = "Apple Frangipan Tart",
            mealCountry = "British",
            mealCategory = "Dessert",
            mealInstruction = "Preheat the oven to 200C/180C Fan/Gas 6. Put the biscuits in a large re-sealable freezer bag and bash with a rolling pin into fine crumbs. Melt the butter in a small pan, then add the biscuit crumbs and stir until coated with butter. Tip into the tart tin and, using the back of a spoon, press over the base and sides of the tin to give an even layer. Chill in the fridge while you make the filling. Cream together the butter and sugar until light and fluffy. You can do this in a food processor if you have one. Process for 2-3 minutes. Mix in the eggs, then add the ground almonds and almond extract and blend until well combined. Peel the apples, and cut thin slices of apple. Do this at the last minute to prevent the apple going brown. Arrange the slices over the biscuit base. Spread the frangipane filling evenly on top. Level the surface and sprinkle with the flaked almonds. Bake for 20-25 minutes until golden-brown and set. Remove from the oven and leave to cool for 15 minutes. Remove the sides of the tin. An easy way to do this is to stand the tin on a can of beans and push down gently on the edges of the tin. Transfer the tart, with the tin base attached, to a serving plate. Serve warm with cream, crème fraiche or ice cream.",
            mealThumb = "https://www.themealdb.com/images/media/meals/wxywrq1468235067.jpg",
            mealYoutubeLink = "https://www.youtube.com/watch?v=rp8Slv4INLk"
        )
        dao.insertFavorite(meal)

        val result = dao.getMealById("52768")
        Assert.assertEquals(meal, result)
    }

    @Test
    fun updateFavorite_updatesDataCorrectly() = runBlocking {
        val meal = MealDB(
            mealId = "52768",
            mealName = "Apple Frangipan Tart",
            mealCountry = "British",
            mealCategory = "Dessert",
            mealInstruction = "Preheat the oven to 200C/180C Fan/Gas 6. Put the biscuits in a large re-sealable freezer bag and bash with a rolling pin into fine crumbs. Melt the butter in a small pan, then add the biscuit crumbs and stir until coated with butter. Tip into the tart tin and, using the back of a spoon, press over the base and sides of the tin to give an even layer. Chill in the fridge while you make the filling. Cream together the butter and sugar until light and fluffy. You can do this in a food processor if you have one. Process for 2-3 minutes. Mix in the eggs, then add the ground almonds and almond extract and blend until well combined. Peel the apples, and cut thin slices of apple. Do this at the last minute to prevent the apple going brown. Arrange the slices over the biscuit base. Spread the frangipane filling evenly on top. Level the surface and sprinkle with the flaked almonds. Bake for 20-25 minutes until golden-brown and set. Remove from the oven and leave to cool for 15 minutes. Remove the sides of the tin. An easy way to do this is to stand the tin on a can of beans and push down gently on the edges of the tin. Transfer the tart, with the tin base attached, to a serving plate. Serve warm with cream, crème fraiche or ice cream.",
            mealThumb = "https://www.themealdb.com/images/media/meals/wxywrq1468235067.jpg",
            mealYoutubeLink = "https://www.youtube.com/watch?v=rp8Slv4INLk"
        )
        dao.insertFavorite(meal)

        val updatedMeal = MealDB(
            mealId = "52768",
            mealName = "Apple & Blackberry Crumble",
            mealCountry = "British",
            mealCategory = "Dessert",
            mealInstruction = "Heat oven to 190C/170C fan/gas 5. Tip the flour and sugar into a large bowl. Add the butter, then rub into the flour using your fingertips to make a light breadcrumb texture. Do not overwork it or the crumble will become heavy. Sprinkle the mixture evenly over a baking sheet and bake for 15 mins or until lightly coloured. Meanwhile, for the compote, peel, core and cut the apples into 2cm dice. Put the butter and sugar in a medium saucepan and melt together over a medium heat. Cook for 3 mins until the mixture turns to a light caramel. Stir in the apples and cook for 3 mins. Add the blackberries and cinnamon, and cook for 3 mins more. Cover, remove from the heat, then leave for 2-3 mins to continue cooking in the warmth of the pan. To serve, spoon the warm fruit into an ovenproof gratin dish, top with the crumble mix, then reheat in the oven for 5-10 mins. Serve with vanilla ice cream.",
            mealThumb = "https://www.themealdb.com/images/media/meals/xvsurr1511719182.jpg",
            mealYoutubeLink = "https://www.youtube.com/watch?v=4vhcOwVBDO4"
        )
        dao.updateFavorite(updatedMeal)

        val result = dao.getMealById("52768")
        Assert.assertEquals(updatedMeal, result)
    }
}

//package com.example.easyfood.data.db
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
////import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.easyfood.data.pojo.MealDB
//import kotlinx.coroutines.runBlocking
//import org.junit.*
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import java.io.IOException
//
//@RunWith(JUnit4::class)
//class DaoTest {
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var database: MealsDatabase  // Nova baza podataka MealsDatabase
//    private lateinit var dao: Dao  // Vaš Dao interfejs
//
//    @Before
//    fun init() {
//        // Kreiranje in-memory baze podataka za testiranje
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            MealsDatabase::class.java // Ispravna klasa baze podataka
//        ).allowMainThreadQueries().build()
//
//        dao = database.dao()  // Pristup Dao instanci
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        database.close()
//    }
//
//    @Test
//    fun insertFavorite_insertsDataCorrectly() = runBlocking {
//        val meal = MealDB(
//            mealId = 52768,
//            mealName = "Apple Frangipan Tart",
//            mealCountry = "British",
//            mealCategory = "Dessert",
//            mealInstruction = "Preheat the oven to 200C/180C Fan/Gas 6. Put the biscuits in a large re-sealable freezer bag and bash with a rolling pin into fine crumbs. Melt the butter in a small pan, then add the biscuit crumbs and stir until coated with butter. Tip into the tart tin and, using the back of a spoon, press over the base and sides of the tin to give an even layer. Chill in the fridge while you make the filling. Cream together the butter and sugar until light and fluffy. You can do this in a food processor if you have one. Process for 2-3 minutes. Mix in the eggs, then add the ground almonds and almond extract and blend until well combined. Peel the apples, and cut thin slices of apple. Do this at the last minute to prevent the apple going brown. Arrange the slices over the biscuit base. Spread the frangipane filling evenly on top. Level the surface and sprinkle with the flaked almonds. Bake for 20-25 minutes until golden-brown and set. Remove from the oven and leave to cool for 15 minutes. Remove the sides of the tin. An easy way to do this is to stand the tin on a can of beans and push down gently on the edges of the tin. Transfer the tart, with the tin base attached, to a serving plate. Serve warm with cream, crème fraiche or ice cream.",
//            mealThumb = "https://www.themealdb.com/images/media/meals/wxywrq1468235067.jpg",
//            mealYoutubeLink = "https://www.youtube.com/watch?v=rp8Slv4INLk"
//        )
//        dao.insertFavorite(meal)
//
//        val result = dao.getMealById("52768")
//        Assert.assertEquals(meal, result)
//    }
//
//    @Test
//    fun updateFavorite_updatesDataCorrectly() = runBlocking {
//        val meal = MealDB(
//            mealId = 52768,
//            mealName = "Apple Frangipan Tart",
//            mealCountry = "British",
//            mealCategory = "Dessert",
//            mealInstruction = "Preheat the oven to 200C/180C Fan/Gas 6. Put the biscuits in a large re-sealable freezer bag and bash with a rolling pin into fine crumbs. Melt the butter in a small pan, then add the biscuit crumbs and stir until coated with butter. Tip into the tart tin and, using the back of a spoon, press over the base and sides of the tin to give an even layer. Chill in the fridge while you make the filling. Cream together the butter and sugar until light and fluffy. You can do this in a food processor if you have one. Process for 2-3 minutes. Mix in the eggs, then add the ground almonds and almond extract and blend until well combined. Peel the apples, and cut thin slices of apple. Do this at the last minute to prevent the apple going brown. Arrange the slices over the biscuit base. Spread the frangipane filling evenly on top. Level the surface and sprinkle with the flaked almonds. Bake for 20-25 minutes until golden-brown and set. Remove from the oven and leave to cool for 15 minutes. Remove the sides of the tin. An easy way to do this is to stand the tin on a can of beans and push down gently on the edges of the tin. Transfer the tart, with the tin base attached, to a serving plate. Serve warm with cream, crème fraiche or ice cream.",
//            mealThumb = "https://www.themealdb.com/images/media/meals/wxywrq1468235067.jpg",
//            mealYoutubeLink = "https://www.youtube.com/watch?v=rp8Slv4INLk"
//        )
//        dao.insertFavorite(meal)
//
//        val updatedMeal = MealDB(
//            mealId = 52768,
//            mealName = "Apple & Blackberry Crumble",
//            mealCountry = "British",
//            mealCategory = "Dessert",
//            mealInstruction = "Heat oven to 190C/170C fan/gas 5. Tip the flour and sugar into a large bowl. Add the butter, then rub into the flour using your fingertips to make a light breadcrumb texture. Do not overwork it or the crumble will become heavy. Sprinkle the mixture evenly over a baking sheet and bake for 15 mins or until lightly coloured. Meanwhile, for the compote, peel, core and cut the apples into 2cm dice. Put the butter and sugar in a medium saucepan and melt together over a medium heat. Cook for 3 mins until the mixture turns to a light caramel. Stir in the apples and cook for 3 mins. Add the blackberries and cinnamon, and cook for 3 mins more. Cover, remove from the heat, then leave for 2-3 mins to continue cooking in the warmth of the pan. To serve, spoon the warm fruit into an ovenproof gratin dish, top with the crumble mix, then reheat in the oven for 5-10 mins. Serve with vanilla ice cream.",
//            mealThumb = "https://www.themealdb.com/images/media/meals/xvsurr1511719182.jpg",
//            mealYoutubeLink = "https://www.youtube.com/watch?v=4vhcOwVBDO4"
//        )
//        dao.updateFavorite(updatedMeal)
//
//        val result = dao.getMealById("52768")
//        Assert.assertEquals(updatedMeal, result)
//    }
//
//    // Ostatak testova može ostati isti, uz korišćenje konkretnih podataka po potrebi
//}
