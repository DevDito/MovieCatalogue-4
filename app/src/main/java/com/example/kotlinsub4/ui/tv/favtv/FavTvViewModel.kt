package com.example.kotlinsub4.ui.tv.favtv

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlinsub4.MVDatabase
import com.example.kotlinsub4.ui.tv.pojo.ResultsItem

class FavTvViewModel (application: Application) : AndroidViewModel(application) {
    private val favTv = MutableLiveData<MutableList<ResultsItem>>()
    private val movieCatalogueDatabase: MVDatabase = MVDatabase.getDatabase(getApplication())

    internal val getTvShowList: MutableLiveData<MutableList<ResultsItem>>
        get() {
            return favTv
        }

    internal fun fetchFavTvShows() {
        AsyncTask.execute {
            favTv.postValue(movieCatalogueDatabase.tvDao().getAllTvShow())
        }
    }
}
