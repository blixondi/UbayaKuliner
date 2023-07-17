package com.shem.ubayafood.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shem.ubayafood.model.User
import org.json.JSONArray
import org.json.JSONObject

class UserViewModel(Application: Application): AndroidViewModel(Application) {
    val userLD = MutableLiveData<User>()
    val statusLD = MutableLiveData<String>()
    val TAG = "volleytag"
    private var queue: RequestQueue? = null


    fun login(username: String, password: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://kenhosting.ddns.net/uas-anmp/user/login.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                val result = Gson().fromJson<User>(it, User::class.java)
                userLD.value = result
                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map.set("username", username)
                map.set("password", password)
                return map
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun register(username: String, firstName: String, lastName: String, password: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://kenhosting.ddns.net/uas-anmp/user/register.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("showvoley", it.toString())
                val jsonObject = JSONObject(it)
                statusLD.value = jsonObject.getString("status")
            },
            {
                Log.d("showvoley", it.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map.set("username", username)
                map.set("password", password)
                map.set("first_name",firstName)
                map.set("last_name",lastName)
                return map
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}