package com.shem.ubayafood.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shem.ubayafood.R
import com.shem.ubayafood.model.UKDatabase
//import com.shem.ubayafood.model.UKDatabase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


fun ImageView.loadImage(url: String?, progressBar: ProgressBar) {
    Picasso.get()
        .load(url)
        .resize(400, 400)
        .centerCrop()
        .error(R.drawable.baseline_error_24)
        .into(this, object: Callback {
            override fun onSuccess() {
                progressBar.visibility=View.GONE
            }

            override fun onError(e: Exception?) {
                Log.e("errorcheck",e?.message.toString())
            }
        })

}

@BindingAdapter("android:imageUrl","android:progressBar")
fun loadPhotoURL(view: ImageView, url:String?, pb:ProgressBar){
    view.loadImage(url, pb)
}

const val DB_NAME = "ukdb"

fun buildDB(context: Context): UKDatabase {
    val db = Room.databaseBuilder(
        context.applicationContext,
        UKDatabase::class.java,
        DB_NAME
    ).addMigrations(MIGRATION_1_2).build()

    return db
}

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("alter table foods add column user_id integer not null")
    }
}

val MIGRATION_2_3 = object : Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("create table if not exists `Detail` (`food_id` INTEGER, PRIMARY KEY(`food_id`))")
    }
}