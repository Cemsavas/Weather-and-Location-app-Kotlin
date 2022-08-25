package com.cemsavas.case_cemsavas_accuweather_api

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.cemsavas.case_cemsavas_accuweather_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL="http://dataservice.accuweather.com/" //Api kullandığımız webServisin baseUrlsini sabit değişkene atadım.

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {
    lateinit var Adapter: Adapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var GET: SharedPreferences  //ApiInterface'e edittext'e girilen anahtar kelimeyi search etmesi için SharedPreferences kullandım.
    private lateinit var SET: SharedPreferences.Editor
    lateinit var binding: ActivityMainBinding //layoutta hazırladığım componentleri belleğe çıkarmak için viewBinding kullandım.

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()//Supportbarı gizleyelim.

        binding.recyclerview.setHasFixedSize(true) // true yaparak recyclerview yapısını gelen dataya göre değişmesini engelledim.Recyclerview yapısını korudum.
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = linearLayoutManager//RecyclerViewer için Layout manager olrak LinearLayoutManager'ı tercih ettim.

        binding.button.setOnClickListener{
            val intent=Intent(this,Guncel_Havadurumu::class.java)
            startActivity(intent)
        }

        binding.edttxt.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->  //edittext'e girilen anahtar kelimeyi enter'a basıldığında arama yapması için
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                GET = getSharedPreferences(packageName, MODE_PRIVATE)
                SET = GET.edit()

                getKonum_data() //Konumdatalarını Apiden çekmek için yazdığım fonksiyon


                return@OnKeyListener true
            }
            false
        })

    }

    fun getKonum_data() {

        val anahtarkelime = binding.edttxt.text.toString() //arama satırına yazılan anahtar kelimeyi değişkene atadım.

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) //Retrofit oluşturdum.
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData(sehir = anahtarkelime) // Api'den anahtar kelime girerek retrofit ile çektiğim konum datasını değişkene atadım.

        retrofitData.enqueue(object : Callback<List<Konum_dataItem>?> { // retrofitdatayı çağırdım.
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<Konum_dataItem>?>,
                response: Response<List<Konum_dataItem>?>
            ) {
                val responseBody = response.body()!!

                Adapter = Adapter(baseContext, responseBody) // Çağırdığım dataları RecyclerViewe işledim.
                binding.recyclerview.adapter = Adapter

            }

            override fun onFailure(
                call: Call<List<Konum_dataItem>?>,
                t: Throwable
            ) {  //Çağırdığım datayı bulamazsam hata logu yazdım.
                Log.d("MainActivity", "Konum Bulunamadı" + t.message)
            }
        })
    }

}
