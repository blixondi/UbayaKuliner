package com.shem.ubayafood.view

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
import com.google.android.material.textfield.TextInputEditText
import com.shem.ubayafood.R
import com.shem.ubayafood.viewmodel.UserViewModel
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.shem.ubayafood.databinding.FragmentFavoriteBinding
import com.shem.ubayafood.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var userVM: UserViewModel
    private lateinit var dataBinding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,R.layout.fragment_register,container,false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCreateAccount = view.findViewById<Button>(R.id.btnCreateAccount)
        userVM = ViewModelProvider(this)[UserViewModel::class.java]
        btnCreateAccount.setOnClickListener {
            val txtNewUsername = dataBinding.txtNewUsername?.text.toString()
            val txtNewFirstName = dataBinding.txtNewFirstName?.text.toString()
            val txtNewLastName = dataBinding.txtNewLastName?.text.toString()
            val txtNewPassword = dataBinding.txtNewPassword?.text.toString()
            val txtNewConfirmPassword = dataBinding.txtConfirmNewPassword?.text.toString()
            if (
                txtNewUsername == "" ||
                txtNewFirstName == "" ||
                txtNewLastName == "" ||
                txtNewPassword == "" ||
                txtNewConfirmPassword == ""
            ){
                Toast.makeText(activity, "Harap isi kolom-kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (txtNewPassword != txtNewConfirmPassword) {
                Toast.makeText(activity, "Harap isi dari dua kolom password sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            Log.e("status", "enter here")
            userVM.register(txtNewUsername, txtNewFirstName, txtNewLastName, txtNewPassword)
            userVM.statusLD.observe(this){status ->
                if (status == "OK"){
                    Toast.makeText(activity, "Register sukses, silahkan login", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
//            Log.e("status", "enter here")

        }
    }
}