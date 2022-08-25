package com.cemsavas.case_cemsavas_accuweather_api

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cemsavas.case_cemsavas_accuweather_api.databinding.ActivityHavadurumuDataBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Havadurumu : AppCompatActivity() {
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    lateinit var binding:ActivityHavadurumuDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHavadurumuDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        getSicaklik_data()//Sıcaklıkdatalarını Apiden çekmek için yazdığım fonksiyon

        val intent=intent
        val aLocalizedname=intent.getStringExtra("iLocalizedname")
        binding.textView.text=aLocalizedname

        val aKey=intent.getStringExtra("iKey")
        binding.textView4.text=aKey.toString()
    }
    private fun getSicaklik_data() {
        val retrofitBuilder_Sicaklik = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()) //Retrofit oluşturdum.
                .baseUrl(BASE_URL)
                .build()
                .create(Sicaklik_ApiInterface::class.java)

                val retrofitData_Sicaklik = retrofitBuilder_Sicaklik.getData_Sicaklik(q = binding.textView4.text.toString())

                retrofitData_Sicaklik.enqueue(object : Callback<List<Sicaklik_dataItem>?> {
                override fun onResponse(
                    call: Call<List<Sicaklik_dataItem>?>,
                    response: Response<List<Sicaklik_dataItem>?>
                ) {
                    val myStringBuilder4=StringBuilder()
                    val myStringBuilder3= StringBuilder()
                    val myStringBuilder2=StringBuilder()
                    val responseBody=response.body()!!

                    for (Sicaklik_data in responseBody){
                        myStringBuilder2.append(Sicaklik_data.Temperature.Value)
                        myStringBuilder2.append(Sicaklik_data.Temperature.Unit)

                        myStringBuilder3.append(Sicaklik_data.DateTime)

                        myStringBuilder4.append(Sicaklik_data.IsDaylight)

                        binding.textView7.text=myStringBuilder2
                        binding.textView9.text=myStringBuilder3
                        binding.textView8.text=myStringBuilder4
                    }
                }

                override fun onFailure(call: Call<List<Sicaklik_dataItem>?>, t: Throwable) {

                }
            })

    }
}