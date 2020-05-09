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

class CommentsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    // TODO: Implement the ViewModel


    private val comments = MutableLiveData<Resource<List<DataMain>>>()
    private val compositeDisposable = CompositeDisposable()

    fun init(string: String) {
        fetchComments(string)
    }


    private fun fetchComments(string: String) {
        comments.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getComments(string)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ commentList ->
                    comments.postValue(Resource.success(commentList))
                }, { throwable ->
                    comments.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getComments(): LiveData<Resource<List<DataMain>>> {
        return comments
    }

}
