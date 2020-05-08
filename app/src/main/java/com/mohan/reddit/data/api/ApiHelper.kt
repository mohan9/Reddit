class ApiHelper(private val apiService: ApiService) {

    fun getPosts() = apiService.getPosts()

}