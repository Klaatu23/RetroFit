package com.klaatu.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val URL_COUNTRY_API = "https://restcountries.eu/"
        var paises = mutableListOf<Country>()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ListV = findViewById<ListView>(R.id.listview)
        textView.setText("Datos de Paises:")
        val retro = Retrofit.Builder()
            .baseUrl(URL_COUNTRY_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retro.create(CountriesService::class.java)

        val countryRequest = service.listCountries()

        countryRequest.enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {

                val allCountry = response.body()
                paises = allCountry as MutableList<Country>

                /*  for(c in allCountry!!)
                {
                   Log.v(
                        MainActivity::class.simpleName,
                        "Nombre:${c.name}  \n Capital:${c.capital}  \n Idioma:${c.languages}"
                    )

                }*/

            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "Ha fallado...")
            }

        }
        )

        val adaptador = ArrayAdapter<Country>(this, android.R.layout.simple_list_item_1, paises)
        ListV.adapter = adaptador
    }
}