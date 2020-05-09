package com.mohan.reddit.data.api


class ApiHelper(private val apiService: ApiService) {
    fun getPosts() = apiService.getPosts()
    fun getComments(string: String) = apiService.getComments(string)
}