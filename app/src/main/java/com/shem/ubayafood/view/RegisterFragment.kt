package com.shem.ubayafood.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.shem.ubayafood.R
import com.shem.ubayafood.viewmodel.UserViewModel
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment() {
    private lateinit var userVM: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCreateAccount = view.findViewById<Button>(R.id.btnCreateAccount)
        userVM = ViewModelProvider(this)[UserViewModel::class.java]
        btnCreateAccount.setOnClickListener {
            val txtNewUsername = view.findViewById<TextInputEditText>(R.id.txtNewUsername)?.text.toString()
            val txtNewFirstName = view.findViewById<TextInputEditText>(R.id.txtNewFirstName)?.text.toString()
            val txtNewLastName = view.findViewById<TextInputEditText>(R.id.txtNewLastName)?.text.toString()
            val txtNewPassword = view.findViewById<TextInputEditText>(R.id.txtNewPassword)?.text.toString()
            val txtNewConfirmPassword = view.findViewById<TextInputEditText>(R.id.txtConfirmNewPassword)?.text.toString()
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