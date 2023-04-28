package me.proton.jobforandroid.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.proton.jobforandroid.retrofit.databinding.ActivityMainBinding
import me.proton.jobforandroid.retrofit.retrofit.PostApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val postApi = retrofit.create(PostApi::class.java)

        binding.btnGet.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val post = postApi.getPostById(7)
                runOnUiThread {
                    binding.tvTitle.text = post.title
                    binding.tvBody.text = post.body
                }
            }
        }
    }
}