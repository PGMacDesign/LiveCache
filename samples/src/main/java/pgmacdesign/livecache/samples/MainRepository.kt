package pgmacdesign.livecache.samples

import android.arch.lifecycle.LiveData
import com.mastercard.labs.android.livecache.LiveResource
import com.mastercard.labs.android.livecache.cache.CachePolicy
import com.mastercard.labs.android.livecache.concurrent.AppExecutors
import com.mastercard.labs.android.livecache.concurrent.AppExecutors.diskIO
import com.mastercard.labs.android.livecache.concurrent.AppExecutors.mainThread
import com.mastercard.labs.android.livecache.concurrent.AppExecutors.networkIO
import com.mastercard.labs.android.livecache.concurrent.IAppExecutors
import com.mastercard.labs.android.livecache.utils.Resource
import java.util.concurrent.Executor

class MainRepository {

	var appExecutors: IAppExecutors = object : IAppExecutors {
		override val diskIO: Executor
			get() = AppExecutors.diskIO
		override val networkIO: Executor
			get() = AppExecutors.networkIO
		override val mainThread: Executor
			get() = AppExecutors.mainThread
	}

	fun searchUniversities(query: String): LiveData<Resource<University>> =
		LiveResource.fetch<UniversityResponse, University>(appExecutors,
			isStorable = true,
			preload = true) { remote.getUser(id) /*remote call*/ }
			.mapper { /* map response */ }
			.onResult { /* to execute after mapping */ }
			/* Set caching policy */
			.setCachePolicy(
				CachePolicy<University>()
				/* define here, is cache valid ? */
				.isValid { /* is cache valid ? */ }
				.load { /* load from cache */ }
				.save { /* save into cache */ })
			/* return resource as LiveData */
			.asLiveData();

}