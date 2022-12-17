package com.mamunsproject.grocery_roomdb

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {
    fun insert(items: GroceryItem) = GlobalScope.launch {
        repository.insert(items)
    }


    fun delete(items: GroceryItem) = GlobalScope.launch {
        repository.delete(items)
    }

    fun getAllGroceryItem() = repository.getAllItems()
}