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
import com.shem.ubayafood.databinding.FragmentHistoryDetailBinding
import com.shem.ubayafood.viewmodel.OrderViewModel


class HistoryDetailFragment : Fragment() {
    private lateinit var viewModel: OrderViewModel
    private lateinit var dataBinding:FragmentHistoryDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentHistoryDetailBinding>(
            inflater,R.layout.fragment_history_detail,container,false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var order_id = ""
        arguments?.let{
            order_id = HistoryDetailFragmentArgs.fromBundle(requireArguments()).orderId
        }

        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        viewModel.getHistoryDetail(order_id)

        viewModel.orderDetailLD.observe(viewLifecycleOwner, Observer {
            dataBinding.order = it
        })
    }

}