package com.example.easyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.adapters.CategoriesRecyclerAdapter
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.databinding.FragmentCategoryBinding
import com.example.easyfood.mvvm.CategoryMVVM
import com.example.easyfood.ui.activites.MealActivity
import com.example.easyfood.ui.fragments.HomeFragment.Companion.CATEGORY_NAME

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var myAdapter: CategoriesRecyclerAdapter
    private lateinit var categoryMvvm: CategoryMVVM  // Prilagođeno za testiranje

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = CategoriesRecyclerAdapter()
        categoryMvvm = ViewModelProvider(this)[CategoryMVVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategories()
        onCategoryClick()
    }

    fun injectCategoryMvvm(mockMvvm: CategoryMVVM) {
        this.categoryMvvm = mockMvvm
    }

    fun getMyAdapter(): CategoriesRecyclerAdapter {
        return myAdapter
    }

    private fun onCategoryClick() {
        myAdapter.onItemClicked(object : CategoriesRecyclerAdapter.OnItemCategoryClicked {
            override fun onClickListener(category: Category) {
                val intent = Intent(context, MealActivity::class.java)
                intent.putExtra(CATEGORY_NAME, category.strCategory)
                startActivity(intent)
            }
        })
    }

    private fun observeCategories() {
        categoryMvvm.observeCategories().observe(viewLifecycleOwner, Observer { categories ->
            myAdapter.setCategoryList(categories ?: emptyList())
        })
    }

    private fun prepareRecyclerView() {
        binding.favoriteRecyclerView.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }
}


//package com.example.easyfood.ui.fragments
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.easyfood.R
//import com.example.easyfood.adapters.CategoriesRecyclerAdapter
//import com.example.easyfood.data.pojo.Category
//import com.example.easyfood.databinding.FragmentCategoryBinding
//import com.example.easyfood.mvvm.CategoryMVVM
//import com.example.easyfood.ui.activites.MealActivity
//import com.example.easyfood.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
//
//class CategoryFragment : Fragment(R.layout.fragment_category) {
//    private lateinit var binding: FragmentCategoryBinding
//    private lateinit var myAdapter: CategoriesRecyclerAdapter
//    lateinit var categoryMvvm: CategoryMVVM  // Prilagođeno za testiranje
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        myAdapter = CategoriesRecyclerAdapter()
//        categoryMvvm = ViewModelProvider(this)[CategoryMVVM::class.java]
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentCategoryBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        prepareRecyclerView()
//        observeCategories()
//        onCategoryClick()
//    }
//
//    fun injectCategoryMvvm(mockMvvm: CategoryMVVM) {
//        this.categoryMvvm = mockMvvm
//    }
//
//
////    fun getCategoryMvvm(): CategoryMVVM {
////        return categoryMvvm
////    }
//
//    fun getMyAdapter(): CategoriesRecyclerAdapter {
//        return myAdapter
//    }
//
//    private fun onCategoryClick() {
//        myAdapter.onItemClicked(object : CategoriesRecyclerAdapter.OnItemCategoryClicked {
//            override fun onClickListener(category: Category) {
//                val intent = Intent(context, MealActivity::class.java)
//                intent.putExtra(CATEGORY_NAME, category.strCategory)
//                startActivity(intent)
//            }
//        })
//    }
//
//    fun observeCategories() {
//        categoryMvvm.observeCategories().observe(viewLifecycleOwner, object : Observer<List<Category>> {
//            override fun onChanged(value: List<Category>?) {
//                myAdapter.setCategoryList(value ?: emptyList())
//            }
//        })
//    }
//
//    private fun prepareRecyclerView() {
//        binding.favoriteRecyclerView.apply {
//            adapter = myAdapter
//            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
//        }
//    }
//}
//
//
//
////package com.example.easyfood.ui.fragments
////
////import android.content.Intent
////import android.os.Bundle
////import androidx.fragment.app.Fragment
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import androidx.lifecycle.Observer
////import androidx.lifecycle.ViewModelProviders
////import androidx.recyclerview.widget.GridLayoutManager
////import com.example.easyfood.R
////import com.example.easyfood.adapters.CategoriesRecyclerAdapter
////import com.example.easyfood.data.pojo.Category
////import com.example.easyfood.databinding.FragmentCategoryBinding
////import com.example.easyfood.mvvm.CategoryMVVM
////import com.example.easyfood.ui.activites.MealActivity
////import com.example.easyfood.ui.fragments.HomeFragment
////import com.example.easyfood.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
////
////
////class CategoryFragment : Fragment(R.layout.fragment_category) {
////    private lateinit var binding:FragmentCategoryBinding
////    private lateinit var myAdapter:CategoriesRecyclerAdapter
////    private lateinit var categoryMvvm:CategoryMVVM
////
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        myAdapter = CategoriesRecyclerAdapter()
////        categoryMvvm = ViewModelProviders.of(this)[CategoryMVVM::class.java]
////    }
////
////    override fun onCreateView(
////        inflater: LayoutInflater, container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
////        binding = FragmentCategoryBinding.inflate(inflater,container,false)
////        return binding.root
////    }
////
////
////    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////        prepareRecyclerView()
////        observeCategories()
////        onCategoryClick()
////    }
////    //for testing
////    fun getMyAdapter(): CategoriesRecyclerAdapter {
////        return myAdapter
////    }
////
////
////    private fun onCategoryClick() {
////        myAdapter.onItemClicked(object : CategoriesRecyclerAdapter.OnItemCategoryClicked{
////            override fun onClickListener(category: Category) {
////                val intent = Intent(context, MealActivity::class.java)
////                intent.putExtra(CATEGORY_NAME,category.strCategory)
////                startActivity(intent)
////            }
////        })
////    }
////    fun getCategoryMvvm(): CategoryMVVM {
////        return categoryMvvm
////    }
////
////    private fun observeCategories() {
////        categoryMvvm.observeCategories().observe(viewLifecycleOwner,object : Observer<List<Category>>{
////            override fun onChanged(t: List<Category>?) {
////                myAdapter.setCategoryList(t!!)
////            }
////
////        })
////    }
////
////    private fun prepareRecyclerView() {
////        binding.favoriteRecyclerView.apply {
////            adapter = myAdapter
////            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
////        }
////    }
////
////
////}
//
//
////package com.example.easyfood.ui.fragments
////
////import android.content.Intent
////import android.os.Bundle
////import androidx.fragment.app.Fragment
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import androidx.lifecycle.Observer
////import androidx.lifecycle.ViewModelProviders
////import androidx.recyclerview.widget.GridLayoutManager
////import com.example.easyfood.R
////import com.example.easyfood.adapters.CategoriesRecyclerAdapter
////import com.example.easyfood.data.pojo.Category
////import com.example.easyfood.databinding.FragmentCategoryBinding
////import com.example.easyfood.mvvm.CategoryMVVM
////import com.example.easyfood.ui.activites.MealActivity
////import com.example.easyfood.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
////
////
////class CategoryFragment : Fragment(R.layout.fragment_category) {
////    private lateinit var binding:FragmentCategoryBinding
////    private lateinit var myAdapter:CategoriesRecyclerAdapter
////    private lateinit var categoryMvvm:CategoryMVVM
////
////    companion object {
////        fun callPrepareRecyclerView(fragment: CategoryFragment) {
////            fragment.prepareRecyclerView()
////        }
////
////        fun callObserveCategories(fragment: CategoryFragment) {
////            fragment.observeCategories()
////        }
////
////        fun callOnCategoryClick(fragment: CategoryFragment) {
////            fragment.onCategoryClick()
////        }
////    }
////
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        myAdapter = CategoriesRecyclerAdapter()
////        categoryMvvm = ViewModelProviders.of(this)[CategoryMVVM::class.java]
////    }
////
////    override fun onCreateView(
////        inflater: LayoutInflater, container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
////        binding = FragmentCategoryBinding.inflate(inflater,container,false)
////        return binding.root
////    }
////
////
////    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////        prepareRecyclerView()
////        observeCategories()
////        onCategoryClick()
////    }
////    fun getCategoryMvvm(): CategoryMVVM {
////        return categoryMvvm
////    }
////
////    fun getBinding(): FragmentCategoryBinding {
////        return binding
////    }
////
////
////    fun getMyAdapter(): CategoriesRecyclerAdapter {
////        return myAdapter
////    }
////
////    private fun onCategoryClick() {
////        myAdapter.onItemClicked(object : CategoriesRecyclerAdapter.OnItemCategoryClicked{
////            override fun onClickListener(category: Category) {
////                val intent = Intent(context, MealActivity::class.java)
////                intent.putExtra(CATEGORY_NAME,category.strCategory)
////                startActivity(intent)
////            }
////        })
////    }
////
////    private fun observeCategories() {
////        categoryMvvm.observeCategories().observe(viewLifecycleOwner,object : Observer<List<Category>>{
////            override fun onChanged(value: List<Category>) {
////                myAdapter.setCategoryList(value)
////            }
////        })
////    }
////
////    private fun prepareRecyclerView() {
////        binding.favoriteRecyclerView.apply {
////            adapter = myAdapter
////            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
////        }
////    }
////
////
////}