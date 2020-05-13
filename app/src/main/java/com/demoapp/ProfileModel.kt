package com.demoapp


import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("Age")
    val age: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("ProfileName")
    val profileName: String,
    @SerializedName("UserName")
    val userName: String
)