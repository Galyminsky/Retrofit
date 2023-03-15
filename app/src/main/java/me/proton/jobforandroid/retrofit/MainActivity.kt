package me.proton.jobforandroid.retrofit

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.proton.jobforandroid.retrofit.retrofit.ProductAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvTitle = findViewById<TextView>(R.id.tvTitile)
        val tvDes = findViewById<TextView>(R.id.tvDescription)
        val button = findViewById<Button>(R.id.btn)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val productAPI = retrofit.create(ProductAPI::class.java)


        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = productAPI.getProductById(3)
                runOnUiThread {
                    tvTitle.text = product.title
                    tvDes.text = product.description

                }
            }
        }
    }
}