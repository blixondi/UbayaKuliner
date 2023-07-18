package com.shem.ubayafood.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FoodItemBinding
import com.shem.ubayafood.model.Food

class FoodListAdapter(val foodList:ArrayList<Food>):
    RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>(),
    FoodItemLayoutInterface{

    class FoodViewHolder(var view: FoodItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FoodItemBinding>(inflater, R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.view.food = foodList[position]
        holder.view.listener = this
        holder.view.imgFavorite.setOnClickListener {
//            Toast.makeText(this, "Favorite button pressed",Toast.LENGTH_SHORT).show()
            Log.e("checkfavorite","button pressed")
        }
    }

    fun updateFoodList(newFoodList: ArrayList<Food>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun onButtonDetailClick(v: View) {
        val action = HomeFragmentDirections.actionFoodDetailFragment(v.tag.toString())
        Navigation.findNavController(v).navigate(action)
    }

}