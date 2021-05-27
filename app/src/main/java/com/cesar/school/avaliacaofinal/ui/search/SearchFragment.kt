package com.cesar.school.avaliacaofinal.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesar.school.avaliacaofinal.R
import com.cesar.school.avaliacaofinal.databinding.FragmentSearchBinding
import com.cesar.school.avaliacaofinal.utils.ApiClient
import com.cesar.school.avaliacaofinal.utils.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private lateinit var adapter: Adapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val progressBar = binding.pBar
        progressBar.visibility = View.INVISIBLE

        val recyclerView = binding.recyclerView
        adapter = Adapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        searchViewModel.weatherList.observe(requireActivity(), Observer {
            adapter.submitList(it)
        })

        binding.searchBtn.setOnClickListener {
            searchWeather()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchWeather(){
        val context = requireActivity().applicationContext

        if(!isInternetAvailable(context)){
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            return
        }

        val apiClient = ApiClient().getApiInterface()
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", 0)
        val unit = prefs.getString("temperature unit", null)
        val lang = prefs.getString("description language", null)
        val city = binding.searchText.text.toString()
        val apiKey = resources.getString(R.string.api_key)

        val progressBar = binding.pBar
        progressBar.visibility = View.VISIBLE

        val myApi = apiClient?.getSearchCityWeatherData(city, unit, lang, apiKey)

        myApi?.enqueue(object : Callback<WeatherResponse?> {
            override fun onResponse(call: Call<WeatherResponse?>?, response: Response<WeatherResponse?>?) {
                if (response!!.body() != null) {
                    val weatherData: WeatherResponse = response.body()!!

                    searchViewModel.updateCities(weatherData.responseList)
                    adapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(p0: Call<WeatherResponse?>?, p1: Throwable?) {
                Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false

        val cm  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        } else {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) result = true
                else if(type == ConnectivityManager.TYPE_MOBILE) result = true
            }
        }
        return result
    }
}