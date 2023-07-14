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
import com.google.gson.reflect.TypeToken
import com.shem.ubayafood.model.Food

class FoodViewModel(application: Application): AndroidViewModel(application) {
    val foodLD = MutableLiveData<ArrayList<Food>>()
    val foodDetailLD = MutableLiveData<Food>()
    val foodErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "tag"
    private var queue: RequestQueue? = null

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

                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
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

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

}