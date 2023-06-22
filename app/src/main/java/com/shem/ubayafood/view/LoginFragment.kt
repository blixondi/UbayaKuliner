package com.shem.ubayafood.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.shem.ubayafood.R
import com.shem.ubayafood.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtUsername = view.findViewById<TextInputEditText>(R.id.txtUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.txtPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

    }

}