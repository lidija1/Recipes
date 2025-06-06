package com.example.easyfood.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_information")
data class MealDB(
    @PrimaryKey
    @ColumnInfo(name = "mealId")
    val mealId: String,
    val mealName: String,
    val mealCountry: String,
    val mealCategory:String,
    val mealInstruction:String,
    val mealThumb:String,
    val mealYoutubeLink:String
)
