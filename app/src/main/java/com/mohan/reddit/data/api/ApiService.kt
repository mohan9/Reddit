import com.mohan.reddit.data.model.DataMain
import io.reactivex.Single

interface ApiService {

    fun getPosts(): Single<List<DataMain>>

}