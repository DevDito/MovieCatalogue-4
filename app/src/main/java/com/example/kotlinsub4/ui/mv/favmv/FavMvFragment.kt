package com.example.kotlinsub4.ui.mv.favmv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinsub4.R
import com.example.kotlinsub4.databinding.FavMvFragmentBinding
import com.example.kotlinsub4.ui.mv.MVAdapter
import com.example.kotlinsub4.ui.mv.detailmv.DetailMvActivity
import com.example.kotlinsub4.ui.mv.pojo.ResultsItem

class FavMvFragment : Fragment() {

    private lateinit var favMoviesFragmentBinding: FavMvFragmentBinding
    private lateinit var mViewModel: FavMvViewModel
    private lateinit var alertDialog: AlertDialog

    private val getFavMovie = Observer<List<ResultsItem>> {
        val mAdapter = MVAdapter(it)
        if (it.size > 0) {
            favMoviesFragmentBinding.tvMessage.visibility = View.GONE
            mAdapter.SetOnItemClickListener(object : MVAdapter.OnItemClickListener {
                override fun onItemClick(view: View, model: ResultsItem) {
                    val goToDetailMovie = Intent(view.context, DetailMvActivity::class.java)
                    goToDetailMovie.putExtra(DetailMvActivity.SELECTED_MV, model)
                    startActivity(goToDetailMovie)
                }
            })

            favMoviesFragmentBinding.recyclerView.adapter = mAdapter
        } else {
            favMoviesFragmentBinding.recyclerView.adapter = null
            favMoviesFragmentBinding.tvMessage.visibility = View.VISIBLE
        }

        favMoviesFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        favMoviesFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fav_mv_fragment, container, false)
        mViewModel = ViewModelProviders.of(this).get(FavMvViewModel::class.java)
        favMoviesFragmentBinding.viewmodel = mViewModel
        return favMoviesFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        favMoviesFragmentBinding.recyclerView.layoutManager = layoutManager
        favMoviesFragmentBinding.recyclerView.setHasFixedSize(true)
        favMoviesFragmentBinding.viewmodel = mViewModel

    }

    override fun onResume() {
        super.onResume()
        mViewModel.fetchFavMovies()
        mViewModel.movies.observe(this, getFavMovie)
    }

}
