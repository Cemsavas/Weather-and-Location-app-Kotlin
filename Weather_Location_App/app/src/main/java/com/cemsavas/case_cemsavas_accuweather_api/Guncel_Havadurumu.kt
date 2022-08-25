package com.cemsavas.case_cemsavas_accuweather_api

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cemsavas.case_cemsavas_accuweather_api.databinding.ActivityGuncelHavadurumuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class Guncel_Havadurumu : AppCompatActivity() {
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    lateinit var binding: ActivityGuncelHavadurumuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGuncelHavadurumuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        //User Konum iznini alalım.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {

                        getGuncelSıcalikdata()

                        Toast.makeText(this, "Konum izni alındı", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Kanum izni reddedildi", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }

    }

    private fun getGuncelSıcalikdata() {
        val retrofitBuilder_GuncelSicaklik = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) //Retrofit oluşturdum.
            .baseUrl(BASE_URL)
            .build()
            .create(Sicaklik_ApiInterface::class.java)

        val retrofitData_Sicaklik = retrofitBuilder_GuncelSicaklik.getData_Sicaklik(q="318290")

        retrofitData_Sicaklik.enqueue(object : Callback<List<Sicaklik_dataItem>?> {
            override fun onResponse(
                call: Call<List<Sicaklik_dataItem>?>,
                response: Response<List<Sicaklik_dataItem>?>
            ) {
                val myStringBuilder4=StringBuilder()
                val myStringBuilder3= StringBuilder()
                val myStringBuilder2=StringBuilder()
                val responseBody=response.body()!!

                for (GuncelSicaklik_data in responseBody){
                    myStringBuilder2.append(GuncelSicaklik_data.Temperature.Value)
                    myStringBuilder2.append(GuncelSicaklik_data.Temperature.Unit)

                    myStringBuilder3.append(GuncelSicaklik_data.DateTime)

                    myStringBuilder4.append(GuncelSicaklik_data.IsDaylight)

                    binding.textView11.text=myStringBuilder2
                    binding.textView12.text=myStringBuilder3
                    binding.textView13.text=myStringBuilder4
                }
            }

            override fun onFailure(call: Call<List<Sicaklik_dataItem>?>, t: Throwable) {

            }
        })

    }
}