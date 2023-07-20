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
import com.shem.ubayafood.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.Objects
import kotlin.coroutines.CoroutineContext

class UserViewModel(Application: Application): AndroidViewModel(Application), CoroutineScope {
    val userLD = MutableLiveData<User>()
    val statusLD = MutableLiveData<String>()
    val balanceLD = MutableLiveData<Int>()
    val TAG = "volleytag"
    private var queue: RequestQueue? = null

    private val job = Job()
    fun addUser(user: User) {
        launch {
            val db = buildDB(getApplication())
//            Log.e("user", user.toString())
            db.userDao().insert(user)
            statusLD.postValue("OK")
        }
    }

    fun updateUser(user: User) {
        launch {
            val db = buildDB(getApplication())
//            Log.e("user", user.toString())
            db.userDao().updateUser(user.user_id, user.username, user.firstName, user.lastName, user.password)
            statusLD.postValue("OK")
        }
    }

    fun getBalance(user_id: Int){
        launch {
            val db = buildDB(getApplication())
            balanceLD.postValue(db.userDao().getBalance(user_id))
        }
    }

    fun updateBalance(user: User, balance:Int) {
        launch {
            val db = buildDB(getApplication())
//            Log.e("user", user.toString())
            db.userDao().updateBalance(user.user_id, balance)
            statusLD.postValue("OK")
        }
    }

    fun reduceBalance(user_id: Int, balance:Int) {
        launch {
            val db = buildDB(getApplication())
//            Log.e("user", user.toString())
            db.userDao().reduceBalance(user_id, balance)
            statusLD.postValue("OK")
        }
    }

    fun getUser(id: Int) {
        launch {
            val db = buildDB(getApplication())
//            Log.e("user", id.toString())
            userLD.postValue(db.userDao().selectUser(id))
        }
    }

    fun deleteUser() {
        launch {
            val db = buildDB(getApplication())
//            Log.e("user", id.toString())
            db.userDao().deleteUser()
        }
    }

    fun login(username: String, password: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://kenhosting.ddns.net/uas-anmp/user/login.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                val result = Gson().fromJson<User>(it, User::class.java)
                userLD.value = result
//                Log.e("showvoley", result.toString())
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

    fun update(user: User){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://kenhosting.ddns.net/uas-anmp/user/update.php"
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
                map.set("id", user.user_id.toString())
                map.set("username", user.username)
                map.set("password", user.password)
                map.set("first_name",user.firstName)
                map.set("last_name",user.lastName)
                map.set("balance",user.balance.toString())
//                map.set("status", status)
                return map
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}