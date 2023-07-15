package com.shem.ubayafood.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.HistoryItemBinding
import com.shem.ubayafood.model.Food
import com.shem.ubayafood.model.Order

class HistoryListAdapter(val orderList:ArrayList<Order>):
    RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder>(),
    OrderItemLayoutInterface
    {

    class HistoryViewHolder(var view:HistoryItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<HistoryItemBinding>(inflater, R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.view.order = orderList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
        fun updateOrderList(newOrderList: ArrayList<Order>) {
            orderList.clear()
            orderList.addAll(newOrderList)
            notifyDataSetChanged()
        }

        override fun onButtonDetailClick(v: View) {
            val action = HistoryFragmentDirections.actionItemHistoryToHistoryDetailFragment(v.tag.toString())
            Navigation.findNavController(v).navigate(action)
        }

    }