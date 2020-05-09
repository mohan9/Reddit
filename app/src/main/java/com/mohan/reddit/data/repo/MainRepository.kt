package com.mohan.reddit.data.repo

import com.mohan.reddit.data.api.ApiHelper
import com.mohan.reddit.data.model.DataMain
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getPosts(): Single<List<DataMain>> {
        return apiHelper.getPosts()
    }

    fun getComments(string: String): Single<List<DataMain>> {
        return apiHelper.getComments(string)
    }
}