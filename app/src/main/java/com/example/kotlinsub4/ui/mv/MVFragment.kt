package com.example.kotlinsub4.ui.mv

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
import com.example.kotlinsub4.databinding.FragmentMovieBinding
import com.example.kotlinsub4.ui.mv.detailmv.DetailMvActivity
import com.example.kotlinsub4.ui.mv.pojo.ResponseMv
import com.example.kotlinsub4.ui.mv.pojo.ResultsItem

class MVFragment : Fragment() {

    lateinit var alertDialog: AlertDialog
    lateinit var binding: FragmentMovieBinding
    lateinit var mViewModel: MVFragmentViewModel


    private val getMovies = Observer<ResponseMv> { responseMovies ->
        if (responseMovies != null) {
            if (responseMovies.anError == null) {
                val mAdapter = MVAdapter(responseMovies.results!!)

                mAdapter.SetOnItemClickListener(object : MVAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, model: ResultsItem) {
                        val goToDetailMovie = Intent(view.context, DetailMvActivity::class.java)
                        goToDetailMovie.putExtra(DetailMvActivity.SELECTED_MV, model)
                        startActivity(goToDetailMovie)
                    }
                })

                binding.recyclerView.adapter = mAdapter
            } else {
                alertDialog.setMessage(responseMovies.anError.message)
                alertDialog.show()
            }
        }
        binding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie,container,false)
        mViewModel = ViewModelProviders.of(this).get(MVFragmentViewModel::class.java)
        binding.viewmodel = mViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.doRequestListMovies()
        mViewModel.movies.observe(this, getMovies)
    }
}