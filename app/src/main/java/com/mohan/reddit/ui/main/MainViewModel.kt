package com.mohan.reddit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohan.reddit.data.model.DataMain
import com.mohan.reddit.data.repo.MainRepository
import com.mohan.reddit.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    // TODO: Implement the ViewModel


    private val posts = MutableLiveData<Resource<List<DataMain>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        posts.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ postList ->
                    posts.postValue(Resource.success(postList))
                }, { throwable ->
                    posts.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getPosts(): LiveData<Resource<List<DataMain>>> {
        return posts
    }

}
