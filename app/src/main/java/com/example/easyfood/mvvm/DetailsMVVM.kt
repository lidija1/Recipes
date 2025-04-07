package com.example.easyfood.mvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.easyfood.data.db.MealsDatabase
import com.example.easyfood.data.db.Repository
import com.example.easyfood.data.pojo.MealDB
import com.example.easyfood.data.pojo.MealDetail
import com.example.easyfood.data.pojo.RandomMealResponse
import com.example.easyfood.data.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsMVVM(application: Application) : AndroidViewModel(application) {
    private val mutableMealDetail = MutableLiveData<List<MealDetail>>()
    private val mutableMealBottomSheet = MutableLiveData<List<MealDetail>>()
    private  var allMeals: LiveData<List<MealDB>>
    private  var repository: Repository

    val mealDetail: LiveData<List<MealDetail>>
        get() = mutableMealDetail

    init {
        val mealDao = MealsDatabase.getInstance(application).dao()
        repository = Repository(mealDao)
        allMeals = repository.mealList
    }

    fun getAllSavedMeals() {
        viewModelScope.launch(Dispatchers.Main) {
        }
    }

    fun insertMeal(meal: MealDB) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteMeal(meal)
            withContext(Dispatchers.Main) {
            }
        }
    }

    fun deleteMeal(meal:MealDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMeal(meal)
    }

    fun getMealById(id: String) {
        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
                if (response.isSuccessful) {
                    mutableMealDetail.value = response.body()?.meals
                } else {
                    Log.e(TAG, "Response not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch meal details", t)
            }
        })
    }

//    fun getMealById(id: String) {
//        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
//            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
//                mutableMealDetail.value = response.body()!!.meals
//            }
//
//            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
//                Log.e(TAG, t.message.toString())
//            }
//
//        })
//    }

    fun isMealSavedInDatabase(mealId: String): Boolean {
        var meal: MealDB? = null
        runBlocking(Dispatchers.IO) {
            meal = repository.getMealById(mealId)
        }
        if (meal == null)
            return false
        return true

    }

    fun deleteMealById(mealId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMealById(mealId)
        }
    }

//    fun getMealByIdBottomSheet(id: Int) {
//        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
//            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
//                mutableMealBottomSheet.value = response.body()!!.meals
//            }
//
//            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
//                Log.e(TAG, t.message.toString())
//            }
//
//        })
//    }
fun getMealByIdBottomSheet(id: String) {
    RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
        override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
            // Proveri da li odgovor sadrži obavezne podatke
            response.body()?.let { randomMealResponse ->
                mutableMealBottomSheet.value = randomMealResponse.meals
            } ?: run {
                Log.e(TAG, "Response body is null")
            }
        }

        override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
            Log.e(TAG, t.message.toString())
        }
    })
}


    fun observeMealDetail(): LiveData<List<MealDetail>> {
        return mutableMealDetail
    }

    fun observeMealBottomSheet(): LiveData<List<MealDetail>> {
        return mutableMealBottomSheet
    }

    fun observeSaveMeal(): LiveData<List<MealDB>> {
        return allMeals
    }
}
//package com.example.easyfood.mvvm
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.*
//import com.example.easyfood.data.db.MealsDatabase
//import com.example.easyfood.data.db.Repository
//import com.example.easyfood.data.pojo.MealDB
//import com.example.easyfood.data.pojo.MealDetail
//import com.example.easyfood.data.pojo.RandomMealResponse
//import com.example.easyfood.data.retrofit.RetrofitInstance
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.withContext
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class DetailsMVVM(application: Application) : AndroidViewModel(application) {
//    private val mutableMealDetail = MutableLiveData<List<MealDetail>>()
//    private val mutableMealBottomSheet = MutableLiveData<List<MealDetail>>()
//    private  var allMeals: LiveData<List<MealDB>>
//    private  var repository: Repository
//
//    init {
//        val mealDao = MealsDatabase.getInstance(application).dao()
//        repository = Repository(mealDao)
//        allMeals = repository.mealList
//    }
//
//    fun getAllSavedMeals() {
//        viewModelScope.launch(Dispatchers.Main) {
//        }
//    }
//
//    fun insertMeal(meal: MealDB) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.insertFavoriteMeal(meal)
//            withContext(Dispatchers.Main) {
//            }
//        }
//    }
//
//    fun deleteMeal(meal:MealDB) = viewModelScope.launch(Dispatchers.IO) {
//        repository.deleteMeal(meal)
//    }
//
//    fun getMealById(id: String) {
//        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
//            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
//                mutableMealDetail.value = response.body()!!.meals
//            }
//
//            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
//                Log.e(TAG, t.message.toString())
//            }
//
//        })
//    }
//
//    fun isMealSavedInDatabase(mealId: String): Boolean {
//        var meal: MealDB? = null
//        runBlocking(Dispatchers.IO) {
//            meal = repository.getMealById(mealId)
//        }
//        if (meal == null)
//            return false
//        return true
//
//    }
//
//    fun deleteMealById(mealId:String){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteMealById(mealId)
//        }
//    }
//
//    fun getMealByIdBottomSheet(id: String) {
//        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
//            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
//                mutableMealBottomSheet.value = response.body()!!.meals
//            }
//
//            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
//                Log.e(TAG, t.message.toString())
//            }
//
//        })
//    }
//
//    fun observeMealDetail(): LiveData<List<MealDetail>> {
//        return mutableMealDetail
//    }
//
//    fun observeMealBottomSheet(): LiveData<List<MealDetail>> {
//        return mutableMealBottomSheet
//    }
//
//    fun observeSaveMeal(): LiveData<List<MealDB>> {
//        return allMeals
//    }
//}
//
////package com.example.easyfood.mvvm
////
////import android.app.Application
////import android.util.Log
////import androidx.lifecycle.*
////import com.example.easyfood.R
////import com.example.easyfood.data.db.MealsDatabase
////import com.example.easyfood.data.db.Repository
////import com.example.easyfood.data.pojo.MealDB
////import com.example.easyfood.data.pojo.MealDetail
////import com.example.easyfood.data.pojo.RandomMealResponse
////import com.example.easyfood.data.retrofit.RetrofitInstance
////import kotlinx.coroutines.Dispatchers
////import kotlinx.coroutines.launch
////import kotlinx.coroutines.runBlocking
////import kotlinx.coroutines.withContext
////import retrofit2.Call
////import retrofit2.Callback
////import retrofit2.Response
////
////class DetailsMVVM(application: Application) : AndroidViewModel(application) {
////    private val mutableMealDetail = MutableLiveData<List<MealDetail>>()
////    private val mutableMealBottomSheet = MutableLiveData<List<MealDetail>>()
////    private  var allMeals: LiveData<List<MealDB>>
////    private  var repository: Repository
////
////    init {
////        val mealDao = MealsDatabase.getInstance(application).dao()
////        repository = Repository(mealDao)
////        allMeals = repository.mealList
////    }
////
////    fun getAllSavedMeals() {
////        viewModelScope.launch(Dispatchers.Main) {
////        }
////    }
////
////    fun insertMeal(meal: MealDB) {
////        viewModelScope.launch(Dispatchers.IO) {
////            repository.insertFavoriteMeal(meal)
////            withContext(Dispatchers.Main) {
////            }
////        }
////    }
////
////    fun deleteMeal(meal:MealDB) = viewModelScope.launch(Dispatchers.IO) {
////        repository.deleteMeal(meal)
////    }
////
//////    fun getMealById(id: String) {
//////        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
//////            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
//////                mutableMealDetail.value = response.body()!!.meals
//////            }
//////
//////            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
//////                Log.e(TAG, t.message.toString())
//////            }
//////
//////        })
//////    }
//////fun getMealById(id: String) {
//////    RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
//////        override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
//////            val mealResponse = response.body()
//////            if (mealResponse != null && mealResponse.meals != null && mealResponse.meals.isNotEmpty()) {
//////                // Postavi vrednost direktno kao listu MealDetail objekata
//////                mutableMealDetail.value = mealResponse.meals
//////            } else {
//////                mutableMealDetail.value = emptyList() // Postavi praznu listu ako nema rezultata
//////            }
//////        }
//////
//////        override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
//////            Log.e(TAG, t.message.toString())
//////            mutableMealDetail.value = emptyList() // Postavi praznu listu u slučaju greške
//////        }
//////    })
//////}
////fun getMealById(id: String) {
////    RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
////        override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
////            mutableMealDetail.value = response.body()!!.meals
////        }
////
////        override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
////            Log.e(TAG, t.message.toString())
////        }
////
////    })
////}
////
////
////    fun getMealByIdBottomSheet(id: String) {
////        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
////            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
////                val mealResponse = response.body()
////                if (mealResponse != null && mealResponse.meals != null && mealResponse.meals.isNotEmpty()) {
////                    mutableMealBottomSheet.value = mealResponse.meals
////                } else {
////                    mutableMealBottomSheet.value = emptyList() // Postavi praznu listu ako nema rezultata
////                }
////            }
////
////            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
////                Log.e(TAG, t.message.toString())
////                mutableMealBottomSheet.value = emptyList() // Postavi praznu listu u slučaju greške
////            }
////        })
////    }
////
////
////    fun isMealSavedInDatabase(mealId: String): Boolean {
////        var meal: MealDB? = null
////        runBlocking(Dispatchers.IO) {
////            meal = repository.getMealById(mealId)
////        }
////        if (meal == null)
////            return false
////        return true
////
////    }
////
////    fun deleteMealById(mealId:String){
////        viewModelScope.launch(Dispatchers.IO) {
////            repository.deleteMealById(mealId)
////        }
////    }
////
//////    fun getMealByIdBottomSheet(id: String) {
//////        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
//////            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
//////                mutableMealBottomSheet.value = response.body()!!.meals
//////            }
//////
//////            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
//////                Log.e(TAG, t.message.toString())
//////            }
//////
//////        })
//////    }
////
////    fun observeMealDetail(): LiveData<List<MealDetail>> {
////        return mutableMealDetail
////    }
////
////    fun observeMealBottomSheet(): LiveData<List<MealDetail>> {
////        return mutableMealBottomSheet
////    }
////
////    fun observeSaveMeal(): LiveData<List<MealDB>> {
////        return allMeals
////    }
////
////
////}