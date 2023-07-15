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
import com.shem.ubayafood.model.Order

class OrderViewModel(application: Application):AndroidViewModel(application) {
    val orderLD = MutableLiveData<ArrayList<Order>>()
    val orderDetailLD = MutableLiveData<Order>()
    val orderErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "TAG"
    private var queue:RequestQueue? = null

    fun getFood(){
        loadingLD.value = true
        orderErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://kenhosting.ddns.net/uas-anmp/order/get_order.php"

        val stringRequest= StringRequest(
            Request.Method.GET, url,{
                val sType=object : TypeToken<ArrayList<Order>>(){}.type
                val result= Gson().fromJson<ArrayList<Order>>(it, sType)
                orderLD.value=result
                loadingLD.value=false

                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
                orderErrorLD.value=false
                loadingLD.value=false
            }
        )
        stringRequest.tag=TAG
        queue?.add(stringRequest)
    }

    fun getFoodDetail(order_id: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://kenhosting.ddns.net/uas-anmp/order/get_order.php?id=$order_id"

        val stringRequest= StringRequest(
            Request.Method.GET, url,{
                val result= Gson().fromJson<Order>(it, Order::class.java)
                orderDetailLD.value=result

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