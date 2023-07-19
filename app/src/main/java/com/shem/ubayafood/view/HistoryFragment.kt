package com.shem.ubayafood.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shem.ubayafood.R
import com.shem.ubayafood.viewmodel.OrderViewModel


class HistoryFragment : Fragment() {

    private lateinit var viewModel: OrderViewModel
    private var historyListAdapter = HistoryListAdapter(arrayListOf())

    fun observeViewModel(){
        viewModel.orderLD.observe(viewLifecycleOwner, Observer {
            historyListAdapter.updateOrderList(it)
        })

        viewModel.orderErrorLD.observe(viewLifecycleOwner, Observer{
            val txtErrorHistory = view?.findViewById<TextView>(R.id.txtErrorHistory)
            if(it == true){
                txtErrorHistory?.visibility = View.VISIBLE
            } else{
                txtErrorHistory?.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer{
            val recViewHistory = view?.findViewById<RecyclerView>(R.id.recViewHistory)
            val progressLoadHistory = view?.findViewById<ProgressBar>(R.id.progressLoadHistory)
            if(it == true){
                recViewHistory?.visibility = View.GONE
                progressLoadHistory?.visibility = View.VISIBLE
            } else {
                recViewHistory?.visibility = View.VISIBLE
                progressLoadHistory?.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as? MainActivity)?.supportActionBar?.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        var sharedPreferences = activity!!.getSharedPreferences("LoginDetails",
            Context.MODE_PRIVATE
        )
        var id = sharedPreferences.getInt("user_id", 0)
        viewModel.getHistory(id)


        val recViewHistory = view.findViewById<RecyclerView>(R.id.recViewHistory)
        recViewHistory.layoutManager = LinearLayoutManager(context)
        recViewHistory.adapter = historyListAdapter

        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutHistory)
        val progressLoadHistory = view.findViewById<ProgressBar>(R.id.progressLoadHistory)
        val txtErrorHistory = view.findViewById<TextView>(R.id.txtErrorHistory)
        refreshLayout.setOnRefreshListener {
            recViewHistory.visibility = View.GONE
            txtErrorHistory.visibility = View.GONE
            progressLoadHistory.visibility = View.VISIBLE
            viewModel.getHistory(id)
            refreshLayout.isRefreshing = false
        }
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

}