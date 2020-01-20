package com.example.kotlinsub4.ui.mv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.kotlinsub4.MV
import com.example.kotlinsub4.ui.mv.pojo.ResponseMv

class MVFragmentViewModel (application: Application) : AndroidViewModel(application) {
    private val responseMovies = MutableLiveData<ResponseMv>()

    val movies: MutableLiveData<ResponseMv>
        get() {
            return responseMovies
        }

    fun doRequestListMovies() {
        AndroidNetworking.get("https://api.themoviedb.org/3/discover/movie")
            .addQueryParameter("api_key", MV.MOVIE_DB_API_KEY)
            .addQueryParameter("language", (getApplication() as MV).getLanguage())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(ResponseMv::class.java, object :
                ParsedRequestListener<ResponseMv> {
                override fun onResponse(response: ResponseMv) {
                    responseMovies.postValue(response)
                }

                override fun onError(anError: ANError) {
                    responseMovies.value = ResponseMv(anError)
                }
            })
    }

}