package com.klaatu.retrofit
import retrofit2.Call
import retrofit2.http.GET

interface CountriesService {
    @GET("/rest/v2/all")
    fun listCountries(): Call<List<Country>>
}