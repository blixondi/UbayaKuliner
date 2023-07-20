package com.shem.ubayafood.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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

        var sharedPreferences = activity!!.getSharedPreferences("LoginDetails",
            Context.MODE_PRIVATE
        )
        var user_id = sharedPreferences.getInt("user_id", 0)

        var food_id = ""
        var is_favorite = 0
        var amount = 1
        arguments?.let{
            food_id = FoodDetailFragmentArgs.fromBundle(requireArguments()).foodId
        }

        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        viewModel.checkFavorite(food_id.toInt())
        viewModel.favoriteLD.observe(viewLifecycleOwner, Observer {
            is_favorite = it
        })



        viewModel.getFoodDetail(food_id)

        viewModel.foodDetailLD.observe(viewLifecycleOwner, Observer {
            dataBinding.food = it
        });

        if(is_favorite == 1){
            dataBinding.btnDetailFav.setImageResource(R.drawable.baseline_star_24)
            dataBinding.btnDetailFav.tag = "unfav"
        }

        dataBinding.txtOrderAmount.setText(amount.toString())

        dataBinding.btnMin.setOnClickListener {
            if(amount <= 1){
                Toast.makeText(context,"Minimum order amount is 1",Toast.LENGTH_SHORT).show()
            } else{
                amount -= 1
                dataBinding.txtOrderAmount.setText(amount.toString())
            }
        }

        dataBinding.btnPlus.setOnClickListener {
            amount += 1
            dataBinding.txtOrderAmount.setText(amount.toString())
        }

        dataBinding.btnDetailFav.setOnClickListener {
            if(dataBinding.btnDetailFav.tag.toString() == "fav"){
                dataBinding.btnDetailFav.tag = "unfav"
                viewModel.updateFavourite(food_id.toInt(),1)
                dataBinding.btnDetailFav.setImageResource(R.drawable.baseline_star_24)
                Toast.makeText(context,"Added to favorite",Toast.LENGTH_SHORT).show()
            } else{
                viewModel.updateFavourite(food_id.toInt(),0)
                Toast.makeText(context,"Removed from favorite",Toast.LENGTH_SHORT).show()
                dataBinding.btnDetailFav.setImageResource(R.drawable.baseline_star_border_24)
                dataBinding.btnDetailFav.tag = "fav"
            }
        }

        dataBinding.btnOrder.setOnClickListener {
            val order_address = dataBinding.txtAlamat.text.toString()
            val behalf = dataBinding.txtNamaPembeli.text.toString()
            viewModel.orderFood(food_id, user_id.toString(), amount.toString(), behalf, order_address)
            viewModel.foodOrderLD.observe(this){status->
                if(status == "OK"){
                    viewModel.insertDetail(food_id.toInt(), order_address, behalf)
                    Toast.makeText(activity, "Place order successfull!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

}