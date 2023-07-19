package com.shem.ubayafood.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FoodFavoriteItemBinding
import com.shem.ubayafood.model.Food

class FavoriteListAdapter(val favoriteList:ArrayList<Food>):
    RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>(),
    FavoriteItemLayoutInterface{
    class FavoriteViewHolder(var view: FoodFavoriteItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FoodFavoriteItemBinding>(inflater, R.layout.food_favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.view.food = favoriteList[position]
        holder.view.listener = this
    }

    fun updateFavoriteList(newFavoriteList: List<Food>){
        favoriteList.clear()
        favoriteList.addAll(newFavoriteList)
        notifyDataSetChanged()
    }
    override fun onButtonDetailClick(v: View) {
        val action = FavoriteFragmentDirections.actionFoodDetailFragment2(v.tag.toString())
        Navigation.findNavController(v).navigate(action)
    }

}