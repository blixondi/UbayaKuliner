package com.shem.ubayafood.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FragmentFavoriteBinding
import com.shem.ubayafood.viewmodel.FoodViewModel


class FavoriteFragment : Fragment() {
    private lateinit var viewModel: FoodViewModel
    private lateinit var dataBinding:FragmentFavoriteBinding
    private var favoriteListAdapter = FavoriteListAdapter(arrayListOf())

    fun observeViewModel(){
        viewModel.favoriteFoodLD.observe(viewLifecycleOwner, Observer {
            favoriteListAdapter.updateFavoriteList(it)
            viewModel.addFoods(it)
            if(it.isEmpty()){
                dataBinding.txtErrorFavorite.visibility = View.VISIBLE
            } else{
                dataBinding.txtErrorFavorite.visibility = View.GONE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        viewModel.getFavorites()
        dataBinding.recViewFavorite.layoutManager = LinearLayoutManager(context)
        dataBinding.recViewFavorite.adapter = favoriteListAdapter

        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentFavoriteBinding>(
            inflater,R.layout.fragment_favorite,container,false
        )
        return dataBinding.root
    }
}