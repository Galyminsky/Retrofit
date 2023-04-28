package me.proton.jobforandroid.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.proton.jobforandroid.retrofit.databinding.FragmentLoginBinding
import me.proton.jobforandroid.retrofit.retrofit.AuthRequest
import me.proton.jobforandroid.retrofit.retrofit.MainAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var mainAPI: MainAPI


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        binding.apply {
            bNext.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_productsFragment)
            }
            bSingIn.setOnClickListener {
                auth(
                    AuthRequest(
                        login.text.toString(),
                        password.text.toString()
                    )
                )
            }
        }

    }

    private fun initRetrofit () {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        mainAPI = retrofit.create(MainAPI::class.java)
    }

    private fun auth(authRequest: AuthRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainAPI.auth(authRequest)
            val message = response.errorBody()?.string()?.let{ JSONObject(it).getString("message") }
            requireActivity().runOnUiThread {
                binding.error.text =  message
                val user = response.body()
                if (user != null) {
                    Picasso.get().load(user.image).into(binding.imageView)
                    binding.name.text = user.firstName
                    binding.bNext.visibility = View.VISIBLE
                    viewModel.token.value = user.token
                }
            }

        }
    }

}