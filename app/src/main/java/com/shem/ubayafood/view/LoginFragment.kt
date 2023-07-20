package com.shem.ubayafood.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.shem.ubayafood.R
import com.shem.ubayafood.databinding.FragmentLoginBinding
import com.shem.ubayafood.viewmodel.UserViewModel


class LoginFragment : Fragment() {
    private lateinit var userVM: UserViewModel
    private lateinit var dataBinding:FragmentLoginBinding
//    private val userAdapter = StudentListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,R.layout.fragment_login,container,false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.hide()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.GONE

        userVM = ViewModelProvider(this)[UserViewModel::class.java]

        var sharedPreferences = activity!!.getSharedPreferences("LoginDetails", MODE_PRIVATE)
        var id = sharedPreferences.getInt("user_id", 0)
        Log.e("id", id.toString())
        if (id != 0){
            val action = LoginFragmentDirections.actionHomeFragment()
            Navigation.findNavController(view).navigate(action)
        }
        val action = LoginFragmentDirections.actionHomeFragment()


        dataBinding.btnLogin.setOnClickListener {
            val txtUsername = dataBinding.txtUsername.text.toString()
            val txtPassword = dataBinding.txtPassword.text.toString()

            if (txtPassword.isEmpty() || txtUsername.isEmpty()) {
                Toast.makeText(activity, "Fill in the columns", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userVM = ViewModelProvider(this)[UserViewModel::class.java]
            val userStatus = userVM.login(txtUsername, txtPassword)


        }

        userVM.userLD.observe(viewLifecycleOwner) { user ->
            Log.e("user", user.toString())
            if (user.username != null && user.username.isNotBlank()) {
                    userVM.addUser(user)
                    Log.e("ed", user.username)
                    userVM.statusLD.observe(this){status->
                        if (status == "OK"){
                            val sharedPreferences = activity?.getSharedPreferences("LoginDetails", MODE_PRIVATE)
                            val edit = sharedPreferences?.edit()
                            edit?.putInt("user_id", user.user_id)
                            edit?.apply()
                            val action = LoginFragmentDirections.actionHomeFragment()
                            Navigation.findNavController(requireView()).navigate(action)
                        }
                    }
            } else {
                Toast.makeText(activity, "Invalid username/password", Toast.LENGTH_SHORT).show()
            }
        }

        dataBinding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

}