package com.shem.ubayafood.viewmodel

import android.app.Application
import android.os.IBinder.DeathRecipient
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shem.ubayafood.model.Detail
import com.shem.ubayafood.model.Food
import com.shem.ubayafood.model.User
import com.shem.ubayafood.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class FoodViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val foodLD = MutableLiveData<ArrayList<Food>>()
    val favoriteFoodLD = MutableLiveData<List<Food>>()
    val foodDetailLD = MutableLiveData<Food>()
    val foodErrorLD = MutableLiveData<Boolean>()
    val foodFavoriteErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val foodOrderLD = MutableLiveData<String>()
    val favoriteLD = MutableLiveData<Int>()
    val detailLD = MutableLiveData<Detail>()


    val TAG = "tag"
    private var queue: RequestQueue? = null

    private val job = Job()

    fun addDetails(detail:Detail){
        launch {
            val db = buildDB(getApplication())
            db.detailDao().insert(detail)
        }
    }
    fun getDetails(food_id: Int){
        launch {
            val db = buildDB(getApplication())
            detailLD.postValue(db.detailDao().getDetail(food_id))
        }
    }
    fun addFoods(list: List<Food>) {
        launch {
            val db = buildDB(getApplication())
            db.favouriteDao().insertAll(*list.toTypedArray())
        }
    }

    fun updateFavourite(id: Int, is_favourite: Int){
        launch {
            val db = buildDB(getApplication())
            db.favouriteDao().updateFavourite(id, is_favourite)
        }
    }

    fun getFavorites(){
        loadingLD.value = true
        foodFavoriteErrorLD.value = false
        launch{
            val db = buildDB(getApplication())
            favoriteFoodLD.postValue(db.favouriteDao().selectAllFood())
        }
    }

    fun checkFavorite(food_id: Int){
        launch{
            val db = buildDB(getApplication())
            favoriteLD.postValue(db.favouriteDao().checkFavorite(food_id))
        }
    }

    fun getFood(){
        loadingLD.value = true
        foodErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://kenhosting.ddns.net/uas-anmp/food/get_food.php"

        val stringRequest=StringRequest(
            Request.Method.GET, url,{
                val sType=object : TypeToken<ArrayList<Food>>(){}.type
                val result= Gson().fromJson<ArrayList<Food>>(it, sType)
                foodLD.value=result
                loadingLD.value=false
                Log.e("showvolley", result.toString())
            },
            {
                Log.e("showvoley", it.toString())
                foodErrorLD.value=false
                loadingLD.value=false
            }
        )
        stringRequest.tag=TAG
        queue?.add(stringRequest)
    }

    fun getFoodDetail(food_id: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://kenhosting.ddns.net/uas-anmp/food/get_food.php?id=$food_id"

        val stringRequest=StringRequest(
            Request.Method.GET, url,{
                val result= Gson().fromJson<Food>(it, Food::class.java)
                foodDetailLD.value=result

                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
            }
        )
        stringRequest.tag=TAG
        queue?.add(stringRequest)
    }

    fun orderFood(menu_id: String, user_id: String, quantity: String, behalf: String, address: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://kenhosting.ddns.net/uas-anmp/food/order_food.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("showvoley", it.toString())
                val jsonObject = JSONObject(it)
                foodOrderLD.value = jsonObject.getString("status")
            },
            {
                Log.d("showvoley", it.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map.set("menu_id", menu_id)
                map.set("user_id", user_id)
                map.set("quantity",quantity)
                map.set("behalf",behalf)
                map.set("address",address)
                return map
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}