package com.example.kotlinsub4.ui.tv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.kotlinsub4.MV
import com.example.kotlinsub4.ui.tv.pojo.ResponseTv

class TvViewModel (application: Application) : AndroidViewModel(application){
    private val responseTvShows = MutableLiveData<ResponseTv>()

    internal val getTvShowList: MutableLiveData<ResponseTv>
        get() {
            return responseTvShows
        }

    internal fun doRequestListTvShows() {
        AndroidNetworking.get("https://api.themoviedb.org/3/discover/tv")
            .addQueryParameter("api_key", MV.MOVIE_DB_API_KEY)
            .addQueryParameter("language", (getApplication() as MV).getLanguage())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(ResponseTv::class.java, object :
                ParsedRequestListener<ResponseTv> {

                override fun onResponse(response: ResponseTv) {
                    responseTvShows.postValue(response)
                }

                override fun onError(anError: ANError) {
                    responseTvShows.value = ResponseTv(anError)
                }
            })
    }
}