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
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.shem.ubayafood.R
import com.shem.ubayafood.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private lateinit var userVM: UserViewModel
//    private val userAdapter = StudentListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.hide()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.GONE
        val txtUsername = view.findViewById<TextInputEditText>(R.id.txtUsername)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.txtTopUpPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        userVM = ViewModelProvider(this)[UserViewModel::class.java]
        btnLogin.setOnClickListener {
            if (txtPassword?.text.toString() == "" || txtUsername?.text.toString() == "") {
                Toast.makeText(activity, "Fill in the columns", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var userStatus = userVM.login(txtUsername?.text.toString(), txtPassword?.text.toString())
            userVM.userLD.observe(this) { user ->
                Log.e("e", user.toString())
                if (user.username != null && user.username != ""){
                    val action = LoginFragmentDirections.actionHomeFragment()
                    Navigation.findNavController(it).navigate(action)
                }
                else{
                    Toast.makeText(activity, "Usernane/password salah", Toast.LENGTH_SHORT).show()
                }

            }


        }

        btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

}