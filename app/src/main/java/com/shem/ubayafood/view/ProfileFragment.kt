package com.shem.ubayafood.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FragmentProfileBinding
import com.shem.ubayafood.model.User
import com.shem.ubayafood.viewmodel.UserViewModel


class ProfileFragment : Fragment() {
    private lateinit var dataBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        dataBinding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile,container,false
        )
        return dataBinding.root
    }
    private lateinit var viewModel:UserViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as? MainActivity)?.supportActionBar?.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val btnTO = view.findViewById<Button>(R.id.btnTO)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnUpdateProfile = view.findViewById<Button>(R.id.btnUpdateProfile)

        var sharedPreferences = activity!!.getSharedPreferences("LoginDetails",
            Context.MODE_PRIVATE
        )
        var id = sharedPreferences.getInt("user_id", 0)
        Log.e("id", id.toString())
        viewModel.getUser(id)



        viewModel.userLD.observe(this){ user ->
            dataBinding.user = user

        }
        btnTO.setOnClickListener {
            // put top up amount to sqlite

            // put top up amount to mysql

        }

        btnLogout.setOnClickListener {
            // clear user on sqlite


        }

        btnUpdateProfile.setOnClickListener {
            // put profile changes to sqlite

            // put profile changes to mysql
//            viewModel.update()
        }
    }

}