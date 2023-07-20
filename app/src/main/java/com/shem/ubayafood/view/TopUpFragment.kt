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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FragmentProfileBinding
import com.shem.ubayafood.databinding.FragmentTopUpBinding
import com.shem.ubayafood.util.NotificationHelper
import com.shem.ubayafood.viewmodel.UserViewModel


class TopUpFragment : Fragment() {
    private lateinit var dataBinding: FragmentTopUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentTopUpBinding>(
            inflater, R.layout.fragment_top_up,container,false
        )
        return dataBinding.root
    }
    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        var sharedPreferences = activity!!.getSharedPreferences("LoginDetails",
            Context.MODE_PRIVATE
        )
        var id = sharedPreferences.getInt("user_id", 0)
        Log.e("id", id.toString())
        viewModel.getUser(id)

        dataBinding.balance = "0"

        viewModel.userLD.observe(this){ user ->
            dataBinding.user = user

        }

        dataBinding.btnTopUp.setOnClickListener {
            if (dataBinding.password == "" || dataBinding.password == null){
                Toast.makeText(activity, "Fill in the password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (dataBinding.user?.password != dataBinding.password){
                Toast.makeText(activity, "Password is incorrect", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (dataBinding.balance == "" || dataBinding.balance!! == null || dataBinding.balance!!.toInt() == 0){
                Toast.makeText(activity, "Fill in the balance", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.updateBalance(dataBinding.user!!, dataBinding.balance!!.toInt())
            viewModel.statusLD.observe(this){ status ->
                if (status == "OK"){
                    NotificationHelper(view.context)
                        .createNotification("Success!","Top up successful!",R.drawable.topup)
                    Toast.makeText(activity, "Top up success!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                else{
                    Toast.makeText(activity, "Top up fail. Please try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}