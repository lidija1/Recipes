package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.FavMealCardBinding
//import com.example.easyfood.R
import com.example.easyfood.data.pojo.MealDB
//import com.example.easyfood.databinding.FavMealCardBinding

class FavoriteMealsRecyclerAdapter :
    RecyclerView.Adapter<FavoriteMealsRecyclerAdapter.FavoriteViewHolder>() {
    private var favoriteMeals: List<MealDB> = ArrayList()
    private lateinit var onFavoriteClickListener: OnFavoriteClickListener
    private lateinit var onFavoriteLongClickListener: OnFavoriteLongClickListener
    private var favoriteMealsList = ArrayList<MealDB>()

    fun setFavoriteMealsList(favoriteMeals: List<MealDB>) {
        this.favoriteMeals = favoriteMeals
        notifyDataSetChanged()
    }

    fun getMelaByPosition(position: Int):MealDB{
        return favoriteMeals[position]
    }


    fun setOnFavoriteMealClickListener(onFavoriteClickListener: OnFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener
    }

    fun setOnFavoriteLongClickListener(onFavoriteLongClickListener: OnFavoriteLongClickListener) {
        this.onFavoriteLongClickListener = onFavoriteLongClickListener
    }

    fun getMealByPosition(position: Int): MealDB {
        return favoriteMealsList[position]
    }

    class FavoriteViewHolder(val binding: FavMealCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavMealCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val i = position
        holder.binding.apply {
            tvFavMealName.text = favoriteMeals[position].mealName
            Glide.with(holder.itemView)
                .load(favoriteMeals[position].mealThumb)
                .error(R.drawable.mealtest)
                .into(imgFavMeal)
        }

        holder.itemView.setOnClickListener {
            onFavoriteClickListener.onFavoriteClick(favoriteMeals[position])
        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                onFavoriteLongClickListener.onFavoriteLongCLick(favoriteMeals[i])
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return favoriteMeals.size
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(meal: MealDB)
    }

    interface OnFavoriteLongClickListener {
        fun onFavoriteLongCLick(meal: MealDB)
    }
}