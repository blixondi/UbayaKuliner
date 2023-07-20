package com.shem.ubayafood.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FragmentProfileBinding
import com.shem.ubayafood.model.FavouriteDao
import com.shem.ubayafood.model.User
import com.shem.ubayafood.viewmodel.FoodViewModel
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
    private lateinit var viewModelFood:FoodViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as? MainActivity)?.supportActionBar?.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]



        var sharedPreferences = activity!!.getSharedPreferences("LoginDetails",
            Context.MODE_PRIVATE
        )
        var id = sharedPreferences.getInt("user_id", 0)
        Log.e("id", id.toString())
        viewModel.getUser(id)



        viewModel.userLD.observe(this){ user ->
            dataBinding.user = user

        }
        dataBinding.btnTO.setOnClickListener {
            val action = ProfileFragmentDirections.actionTopUpFragment()
            Navigation.findNavController(it).navigate(action)

        }

        dataBinding.btnLogout.setOnClickListener {
            // clear user on sqlite

            viewModel.update(dataBinding.user!!)
            viewModel.deleteAll()
            val edit = sharedPreferences.edit()
            edit.clear()
            edit.apply()
            viewModel.statusLD.observe(this){status->
                if (status == "OK"){
                    Toast.makeText(activity, "Success upload user data", Toast.LENGTH_SHORT).show()
                    val action = ProfileFragmentDirections.actionLoginFragment()
                    Navigation.findNavController(it).navigate(action)
                }
                else{
                    Toast.makeText(activity, "Failed upload user data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dataBinding.btnUpdateProfile.setOnClickListener {
            // put profile changes to sqlite
            if (dataBinding.user?.password != dataBinding?.oldpassword){
                Toast.makeText(activity, "Password is incorrect", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (dataBinding?.newpassword == "" || dataBinding?.newpassword == null){
                Toast.makeText(activity, "Fill in the new password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            dataBinding.user?.password = dataBinding.newpassword!!
            viewModel.updateUser(dataBinding.user!!)
            viewModel.statusLD.observe(this){status->
                if (status == "OK"){
                    Toast.makeText(activity, "Success editing", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(activity, "Failed editing", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}