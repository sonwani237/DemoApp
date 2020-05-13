package com.demoapp

import android.R.attr.name
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPreferences = getSharedPreferences(Constant.appPref, Context.MODE_PRIVATE)

        val isLogin = sharedPreferences.getString(Constant.login, null)
        if (isLogin!=null && isLogin == "1"){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        login.setOnClickListener {
            if (isValid()){
                val myEdit = sharedPreferences.edit()
                myEdit.putString(Constant.login, "1")
                myEdit.apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isValid(): Boolean{
        if (email.text.isEmpty()){
            return false
        }

        if (password.text.isEmpty()){
            return false
        }

        return true
    }
}
