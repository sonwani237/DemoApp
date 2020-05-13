package com.demoapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {

    @GET("api/timezone.json")
    fun country(): Call<List<String>>

    @GET("api/timezone/{continent}/{country}.json")
    fun getTimeZoneData(@Path("continent") continent :String,
                        @Path("country") country :String): Call<TimeZoneRes>

}