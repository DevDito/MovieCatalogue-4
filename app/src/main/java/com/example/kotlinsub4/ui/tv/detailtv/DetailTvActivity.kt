package com.example.kotlinsub4.ui.tv.detailtv

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
import com.example.kotlinsub4.databinding.ActivityDetailTvBinding
import com.example.kotlinsub4.ui.tv.pojo.ResultsItem
import com.google.android.material.snackbar.Snackbar

class DetailTvActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var Favorite: Boolean = false
    private lateinit var Database: MVDatabase
    private lateinit var tvModel: ResultsItem
    private lateinit var binding: ActivityDetailTvBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Database = MVDatabase.getDatabase(this)
        val viewModel = ViewModelProviders.of(this).get(DetailTvViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tv)

        tvModel = intent.getParcelableExtra(SELECTED_TV)
        viewModel.resultsItem = tvModel
        binding.viewmodel = viewModel

        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + tvModel.posterPath!!).into(binding.imgPoster)

        title = viewModel.resultsItem!!.name

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkFavorite()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        val SELECTED_TV = "selected_tv"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavTvShowDb()
        return true
    }

    private fun setFavTvShowDb() {
        if (Favorite)
            menuItem?.findItem(R.id.action_favorite)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else
            menuItem?.findItem(R.id.action_favorite)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
    }

    private fun checkFavorite(){
        AsyncTask.execute {
            val tvShowDb = Database.tvDao().getTvShowById(tvModel.id)

            Favorite = tvShowDb?.name != null

            runOnUiThread {
                setFavTvShowDb()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_favorite ->{
                if(Favorite){
                    AsyncTask.execute {
                        Database.tvDao().deleteTvShowById(tvModel.id)
                        runOnUiThread {
                            Favorite = !Favorite
                            setFavTvShowDb()
                            Snackbar.make(binding.root,getString(R.string.favorite_success_remove_tv_show), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }else{
                    AsyncTask.execute {
                        Database.tvDao().insert(tvModel)
                        runOnUiThread {
                            Favorite = !Favorite
                            setFavTvShowDb()
                            Snackbar.make(binding.root,getString(R.string.favorite_success_add_tv_show),Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}