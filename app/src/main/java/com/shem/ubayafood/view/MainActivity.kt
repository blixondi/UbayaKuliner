package com.shem.ubayafood.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.shem.ubayafood.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)

        val navView = findViewById<NavigationView>(R.id.navView)
        NavigationUI.setupWithNavController(navView, navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = (navHostFragment).navController
//        Log.e("curr", navController.currentDestination?.label.toString())
        return when (navController.currentDestination?.label.toString()) {
            "Home" -> {
                if (drawerLayout.isOpen) drawerLayout.close()
                else drawerLayout.open()
                super.onSupportNavigateUp()
            }
            "History" -> {
                if (drawerLayout.isOpen) drawerLayout.close()
                else drawerLayout.open()
                super.onSupportNavigateUp()
            }
            "Profile" -> {
                if (drawerLayout.isOpen) drawerLayout.close()
                else drawerLayout.open()
                super.onSupportNavigateUp()
            }
            else -> NavigationUI.navigateUp(navController, drawerLayout)
        }
    }

}