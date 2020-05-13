package com.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    val data = "{\n" +
            "    \"UserName\": \"ram@abc\",\n" +
            "    \"Password\": \"Pass@123\",\n" +
            "    \"ProfileName\": \"Ram Singh\",\n" +
            "    \"Email\": \"ram.singh@sosudatech.com\",\n" +
            "    \"Age\": \"30\"\n" +
            "}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val model = Gson().fromJson(data, ProfileModel::class.java)

        userName.text = model.userName
        password.text = model.password
        proName.text = model.profileName
        email.text = model.email
        age.text = model.age
    }
}
