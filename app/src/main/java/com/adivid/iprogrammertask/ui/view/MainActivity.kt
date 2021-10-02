package com.adivid.iprogrammertask.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adivid.iprogrammertask.adapter.WeatherAdapter
import com.adivid.iprogrammertask.data.database.WeatherEntity
import com.adivid.iprogrammertask.databinding.ActivityMainBinding
import com.adivid.iprogrammertask.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.R

import android.widget.AutoCompleteTextView

import android.widget.ArrayAdapter
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import com.adivid.iprogrammertask.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init1()

        setUpRecyclerView()
        setUpOnClickListners()
        observers()

    }

    private fun init1() {
        Utils.scheduleWorker(this)
        mainViewModel.setUpSuggestions()
    }

    private fun setUpRecyclerView() {
        weatherAdapter = WeatherAdapter()
        binding.recyclerView.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpOnClickListners() {
        binding.buttonSearch.setOnClickListener {
            if (Utils.isNetworkConnected(this)) {
                showOrHideKeyBoard(false)
                if (mainViewModel.validateField(binding.editTextCityName.text.toString()))
                    mainViewModel.getWeatherDataFromDb(binding.editTextCityName.text.toString())
                else showToast("Enter City Name")
            } else {
                showToast("No Internet Connection")
            }

        }
    }

    private fun observers() {
        mainViewModel.searchNotes.observe(this, { response ->

            when (response) {
                is Resource.Success -> {
                    binding.progressBar.showProgressBar(false)
                    if (response.data != null) {
                        val list = ArrayList<WeatherEntity?>()
                        list.add(response.data)
                        weatherAdapter.submitList(list)
                        //weatherAdapter.notifyDataSetChanged()
                        weatherAdapter.notifyItemChanged(0)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.showProgressBar(false)
                    showToast(response.message.toString())
                }
                is Resource.Loading -> {
                    binding.progressBar.showProgressBar(true)
                }
            }
        })

        mainViewModel.cities.observe(this, { response ->
            if (response is Resource.Success) {
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    R.layout.simple_dropdown_item_1line, response.data!!
                )
                binding.editTextCityName.setAdapter(adapter)
            }
        })

    }

    private fun showOrHideKeyBoard(boolean: Boolean) {
        val imm: InputMethodManager? =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (boolean) {
            imm!!.showSoftInput(binding.editTextCityName, InputMethodManager.SHOW_IMPLICIT)
        } else {
            imm!!.hideSoftInputFromWindow(binding.editTextCityName.windowToken, 0)
        }
    }


}