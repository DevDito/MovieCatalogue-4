package com.example.kotlinsub4.ui.tv
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
import com.example.kotlinsub4.databinding.FragmentTvshowBinding
import com.example.kotlinsub4.ui.tv.detailtv.DetailTvActivity
import com.example.kotlinsub4.ui.tv.pojo.ResponseTv
import com.example.kotlinsub4.ui.tv.pojo.ResultsItem

class TVFragment : Fragment() {

    lateinit var alertDialog: AlertDialog
    lateinit var binding: FragmentTvshowBinding
    lateinit var mViewModel: TvViewModel

    val getTvShows = Observer<ResponseTv> { responseTvShows ->
        if (responseTvShows != null) {
            if (responseTvShows.anError == null) {
                val mAdapter = TVAdapter(responseTvShows.results!!)

                mAdapter.SetOnItemClickListener(object : TVAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, model: ResultsItem) {
                        val goToDetailMovie = Intent(view.context, DetailTvActivity::class.java)
                        goToDetailMovie.putExtra(DetailTvActivity.SELECTED_TV, model)
                        startActivity(goToDetailMovie)
                    }
                })

                binding.recyclerView.adapter = mAdapter
            } else {
                alertDialog.setMessage(responseTvShows.anError.message)
                alertDialog.show()
            }
        }
        binding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tvshow,container,false)
        mViewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
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
        mViewModel.doRequestListTvShows()
        mViewModel.getTvShowList.observe(this, getTvShows)
    }
}