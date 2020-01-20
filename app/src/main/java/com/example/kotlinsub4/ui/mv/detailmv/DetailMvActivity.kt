package com.example.kotlinsub4.ui.mv.detailmv

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.kotlinsub4.MVDatabase
import com.example.kotlinsub4.R
import com.example.kotlinsub4.databinding.ActivityDetailMvBinding
import com.example.kotlinsub4.ui.mv.pojo.ResultsItem
import com.google.android.material.snackbar.Snackbar

class DetailMvActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var Database: MVDatabase
    private lateinit var movieModel: ResultsItem
    lateinit var binding : ActivityDetailMvBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Database = MVDatabase.getDatabase(this)
        val viewModel = ViewModelProviders.of(this).get(DetailMVM::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_mv)

        movieModel = intent.getParcelableExtra(SELECTED_MV)
        viewModel.resultsItem = movieModel
        binding.setViewmodel(viewModel)


        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + movieModel.posterPath!!).into(binding.imgPoster)

        setTitle(viewModel.resultsItem!!.title)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkFavorite()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        val SELECTED_MV = "selected_mv"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavMovieDb()
        return true
    }

    private fun setFavMovieDb() {
        if (isFavorite)
            menuItem?.findItem(R.id.action_favorite)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else
            menuItem?.findItem(R.id.action_favorite)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
    }

    private fun checkFavorite(){
        AsyncTask.execute {
            val moviedb = Database.mvDao().getMovieById(movieModel.id)

            isFavorite = moviedb?.title != null

            runOnUiThread {
                setFavMovieDb()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_favorite ->{
                if(isFavorite){
                    AsyncTask.execute {
                        Database.mvDao().deleteMovieById(movieModel.id)
                        runOnUiThread {
                            isFavorite = !isFavorite
                            setFavMovieDb()
                            Snackbar.make(binding.root,getString(R.string.favorite_success_remove_movie),Snackbar.LENGTH_LONG).show()
                        }
                    }
                }else{
                    AsyncTask.execute {
                        Database.mvDao().insert(movieModel)
                        runOnUiThread {
                            isFavorite = !isFavorite
                            setFavMovieDb()
                            Snackbar.make(binding.root,getString(R.string.favorite_success_add_movie),Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

