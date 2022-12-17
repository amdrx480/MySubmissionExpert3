package com.example.mysubmissionexpert.presentation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mysubmissionexpert.R
import com.example.mysubmissionexpert.core.domain.model.TvDb
import com.example.mysubmissionexpert.core.ui.TvDbAdapter
import com.example.mysubmissionexpert.databinding.FragmentHomeBinding
import com.example.mysubmissionexpert.presentation.ui.detail.DetailTvDbActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View { _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showHomeTvDb()
    }

    private fun showHomeTvDb() {
        if (activity != null) {
            val listTvDbAdapter = TvDbAdapter()
            listTvDbAdapter.setOnItemClickTvDbCallback(object : TvDbAdapter.OnItemClickTvDbCallback {
                override fun onItemTvDbClicked(tvDb: TvDb) {
                    val intent = Intent(activity, DetailTvDbActivity::class.java)
                    intent.putExtra(DetailTvDbActivity.EXTRA_DATA, tvDb)
                    startActivity(intent)
                }
            })
            homeViewModel.tvDb.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is com.example.mysubmissionexpert.core.data.Resource.Loading -> showLoading(true)
                        is com.example.mysubmissionexpert.core.data.Resource.Success -> {
                            showLoading(false)
                            it.data?.let { data -> listTvDbAdapter.setData(data) }
                        }
                        is com.example.mysubmissionexpert.core.data.Resource.Error -> {
                            showLoading(false)
                            binding.progressBar.visibility = View.GONE
                            binding.ivError.visibility = View.VISIBLE
                            Glide.with(this)
                                .load(R.drawable.error)
                            it.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
            binding.apply {
                rvTvDb.layoutManager = LinearLayoutManager(context)
                rvTvDb.setHasFixedSize(true)
                rvTvDb.adapter = listTvDbAdapter

                btnFabFavorite.setOnClickListener {
                    val uri = Uri.parse("mysubmissionexpert://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
            binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}