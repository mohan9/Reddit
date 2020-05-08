package com.mohan.reddit.data.api


class ApiHelper(private val apiService: ApiService) {
    fun getPosts() = apiService.getPosts()
}