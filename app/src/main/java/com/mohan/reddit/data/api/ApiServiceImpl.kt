import com.mohan.reddit.data.api.ApiService
import com.mohan.reddit.data.model.DataMain
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl : ApiService {

    override fun getPosts(): Single<List<DataMain>> {
        return Rx2AndroidNetworking.get("https://www.reddit.com/r/subreddit/random.json")
            .build()
            .getObjectListSingle(DataMain::class.java)
    }

    override fun getComments(string: String): Single<List<DataMain>> {
        return Rx2AndroidNetworking.get("https://www.reddit.com$string.json")
            .build()
            .getObjectListSingle(DataMain::class.java)
    }

}