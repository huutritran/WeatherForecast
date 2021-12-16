package com.example.weatherforecast.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.databinding.FragmentSearchBinding
import com.example.weatherforecast.domain.usecases.SearchWeatherFailure
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var adapter: WeatherInfoAdapter
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeData()
    }

    private fun setupRecyclerView() {
        adapter = WeatherInfoAdapter()
        val linearLayoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        val dividerItemDecoration =
            DividerItemDecoration(this.context, linearLayoutManager.orientation)

        with(binding.recyclerView) {
            this.adapter = this@SearchFragment.adapter
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun subscribeData() {
        viewModel.failure.observe(viewLifecycleOwner) { failure ->
            Snackbar.make(binding.root, getErrorMessage(failure), Snackbar.LENGTH_LONG).show()
        }

        viewModel.weatherData.observe(viewLifecycleOwner) { weatherInfoList ->
            weatherInfoList.map { info -> WeatherInfoItem.fromWeatherInfo(info, requireContext()) }
                .also {
                    adapter.updateData(it)
                }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun getErrorMessage(failure: Failure): String {
        return when(failure){
            is SearchWeatherFailure.NotFound -> getString(R.string.error_weather_not_found)
            is SearchWeatherFailure.SearchMinLength -> getString(R.string.error_search_key_min_length)
            is Failure.ServerError -> getString(R.string.error_server)
            else -> getString(R.string.error_unexpected)
        }
    }
}