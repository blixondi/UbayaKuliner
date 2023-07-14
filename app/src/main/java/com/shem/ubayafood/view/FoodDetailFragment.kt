package com.shem.ubayafood.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FragmentFoodDetailBinding
import com.shem.ubayafood.viewmodel.FoodViewModel

class FoodDetailFragment : Fragment() {
    private lateinit var viewModel: FoodViewModel
    private lateinit var dataBinding:FragmentFoodDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentFoodDetailBinding>(
            inflater,R.layout.fragment_food_detail, container, false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var food_id = ""
        arguments?.let{
            food_id = FoodDetailFragmentArgs.fromBundle(requireArguments()).foodId
        }

        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        viewModel.getFoodDetail(food_id)

        viewModel.foodDetailLD.observe(viewLifecycleOwner, Observer {
            dataBinding.food = it
        });
    }

}