package me.proton.jobforandroid.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.proton.jobforandroid.retrofit.adapter.ProductAdapter
import me.proton.jobforandroid.retrofit.databinding.FragmentProductsBinding
import me.proton.jobforandroid.retrofit.retrofit.MainAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var mainAPI: MainAPI
    private lateinit var adapter: ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        initRecyclerView()
        viewModel.token.observe(viewLifecycleOwner){ token ->
            CoroutineScope(Dispatchers.IO).launch {
                val list = mainAPI.getAllProducts(token)
                requireActivity().runOnUiThread {
                    adapter.submitList(list.products)
                }
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

    private fun initRecyclerView() = with(binding){
        adapter = ProductAdapter()
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter
    }

}