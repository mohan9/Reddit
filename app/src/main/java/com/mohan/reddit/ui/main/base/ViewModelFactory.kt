package com.mohan.reddit.ui.main.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohan.reddit.data.api.ApiHelper
import com.mohan.reddit.data.repo.MainRepository
import com.mohan.reddit.ui.main.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}