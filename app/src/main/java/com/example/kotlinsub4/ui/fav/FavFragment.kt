package com.example.kotlinsub4.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinsub4.R
import com.example.kotlinsub4.databinding.FragmentFavBinding

class FavFragment : Fragment() {

    private lateinit var favoriteViewModel: FavViewModel
    private lateinit var binding: FragmentFavBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav, container, false)
        favoriteViewModel = ViewModelProviders.of(this).get(FavViewModel::class.java)
        binding.viewmodel = favoriteViewModel
        binding.viewPager.adapter = SectionsPagerAdapter(binding.root.context, childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)

        return binding.root
    }

}