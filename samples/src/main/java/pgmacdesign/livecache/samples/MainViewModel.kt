package pgmacdesign.livecache.samples

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.mastercard.labs.android.livecache.RepoRequest
import com.mastercard.labs.android.livecache.utils.Resource

class MainViewModel : ViewModel() {

	private var mainRepository : MainRepository = MainRepository();
	private var liveData: MediatorLiveData<Resource<University>>  = MediatorLiveData()

	fun getUser(query: String?) {
		if(query == null){
			return
		}
		RepoRequest(myLifeCycle) { mainRepository.searchUniversities(query) }
			.onLoading { /* on loading event */ }
			.onPreload { /* on preload event */ }
			.onSuccess { /* on success event*/ }
			.onError { /* on error */ }
			.execute()

		val source: LiveData<Resource<University>> = mainRepository.searchUniversities(query);
		this.liveData.addSource(source) { res ->
			if (res == null) {
				liveData.removeSource(source)
			} else {
				liveData.removeSource(source)
				liveData.setValue(res)
			}
		}
	}
}
