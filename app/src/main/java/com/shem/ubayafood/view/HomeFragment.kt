package com.shem.ubayafood.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shem.ubayafood.R
import com.shem.ubayafood.viewmodel.FoodViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: FoodViewModel
    private var foodListAdapter = FoodListAdapter(arrayListOf())

    fun observeViewModel(){
        viewModel.foodLD.observe(viewLifecycleOwner, Observer {
            foodListAdapter.updateFoodList(it)
            viewModel.addFoods(it)
        })

        viewModel.foodErrorLD.observe(viewLifecycleOwner, Observer{
            val txtErrorFood = view?.findViewById<TextView>(R.id.txtErrorFood)
            if(it == true){
                txtErrorFood?.visibility = View.VISIBLE
            } else{
                txtErrorFood?.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer{
            val recViewFood = view?.findViewById<RecyclerView>(R.id.recViewFood)
            val progressLoadFood = view?.findViewById<ProgressBar>(R.id.progressLoadFood)
            if(it == true){
                recViewFood?.visibility = View.GONE
                progressLoadFood?.visibility = View.VISIBLE
            } else {
                recViewFood?.visibility = View.VISIBLE
                progressLoadFood?.visibility = View.GONE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as? MainActivity)?.supportActionBar?.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        viewModel.getFood()
        val recViewFood = view.findViewById<RecyclerView>(R.id.recViewFood)
        recViewFood.layoutManager = LinearLayoutManager(context)
        recViewFood.adapter = foodListAdapter

        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)
        val txtErrorFood = view.findViewById<TextView>(R.id.txtErrorFood)
        val progressLoadFood = view.findViewById<ProgressBar>(R.id.progressLoadFood)
        refreshLayout.setOnRefreshListener {
            recViewFood.visibility = View.GONE
            txtErrorFood.visibility = View.GONE
            progressLoadFood.visibility = View.VISIBLE
            viewModel.getFood()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}