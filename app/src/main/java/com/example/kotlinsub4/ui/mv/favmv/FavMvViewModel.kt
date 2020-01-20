package com.example.kotlinsub4.ui.mv.favmv

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlinsub4.MVDatabase
import com.example.kotlinsub4.ui.mv.pojo.ResultsItem

class FavMvViewModel (application: Application) : AndroidViewModel(application) {
    private val favMv = MutableLiveData<MutableList<ResultsItem>>()
    private val movieCatalogueDatabase: MVDatabase = MVDatabase.getDatabase(getApplication())

    internal val movies: MutableLiveData<MutableList<ResultsItem>>
        get() {
            return favMv
        }

    internal fun fetchFavMovies() {
        AsyncTask.execute {
            favMv.postValue(movieCatalogueDatabase.mvDao().getAllMovie())
        }
    }
}