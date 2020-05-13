package com.demoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var mList: MutableList<APIRes> = ArrayList<APIRes>()
    var mContinent: MutableList<String> = ArrayList<String>()
    var mCountry: MutableList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val endpoint : Endpoint = ApiClient().getClient()?.create(Endpoint::class.java)!!
        val call: Call<List<String>> = endpoint.country()
        call.enqueue(object : Callback<List<String>?> {
            override fun onResponse(call: Call<List<String>?>, response: Response<List<String>?>) {

                Log.e("Res", ">>>   "+Gson().toJson(response))

                parseRes(response.body())
            }
            override fun onFailure(call: Call<List<String>?>, t: Throwable) {
                Log.e("Error", ">>> "+t.message)
            }
        })

        val sharedPreferences = getSharedPreferences(Constant.appPref, Context.MODE_PRIVATE)

        logout.setOnClickListener {
            val myEdit = sharedPreferences.edit()
            myEdit.putString(Constant.login, "0")
            myEdit.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }

    private fun parseRes(body: List<String>?) {
        Log.e("Res", "body >>>   "+Gson().toJson(body))
        mList.clear()
        if (body!!.isNotEmpty()){
            for (i in body.indices){
                val cotinent = body[i].split("/")
                if (cotinent.size == 2){
                    val apiRes = APIRes(cotinent[0], cotinent[1] )
                    mList.add(apiRes)
                }
            }
        }

        if (mContinent.size == 0){
            mContinent.clear()
            mContinent.add("Select Continent")
            for (j in 0 until mList.size){
                if (!mContinent.contains(mList[j].continent)){
                    mContinent.add(mList[j].continent)
                }
            }
        }

        val continentAdapter: ArrayAdapter<String?> = ArrayAdapter(applicationContext, R.layout.spinner_item, mContinent as List<String?>)
        continent.adapter = continentAdapter

        continent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (parent.getItemAtPosition(position).toString() != "Select Continent") {
                    setCountry(parent.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }

    private fun setCountry(mContinent: String) {
        mCountry.clear()
        mCountry.add("Select Country")
        for (j in 0 until mList.size){
            if (mContinent == mList[j].continent){
                mCountry.add(mList[j].country)
            }
        }

        val countryAdapter: ArrayAdapter<String?> = ArrayAdapter(applicationContext, R.layout.spinner_item, mCountry as List<String?>)
        country.adapter = countryAdapter

        country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (parent.getItemAtPosition(position).toString() != "Select Country") {
                    getData(mContinent, parent.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun getData(mContinent: String, country: String) {
        Log.e("Selection", ">>> $mContinent    $country")

        val endpoint : Endpoint = ApiClient().getClient()?.create(Endpoint::class.java)!!
        val call: Call<TimeZoneRes> = endpoint.getTimeZoneData(mContinent, country)
        call.enqueue(object : Callback<TimeZoneRes?> {
            override fun onResponse(call: Call<TimeZoneRes?>, response: Response<TimeZoneRes?>) {

                Log.e("TimeZone", ">>>   "+Gson().toJson(response.body()))

                setTimeZoneData(response.body())

            }
            override fun onFailure(call: Call<TimeZoneRes?>, t: Throwable) {
                Log.e("Error", ">>> "+t.message)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setTimeZoneData(body: TimeZoneRes?) {
        localDate.text = "timezone "+body?.timezone
        dayWeek.text = "dayOfWeek "+body?.dayOfWeek.toString()
        dayYear.text = "dayOfYear "+body?.dayOfYear.toString()
        weekNum.text = "weekNumber "+body?.weekNumber.toString()
    }

}
