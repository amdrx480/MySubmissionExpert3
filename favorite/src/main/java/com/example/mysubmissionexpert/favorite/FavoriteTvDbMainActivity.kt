package com.example.mysubmissionexpert.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionexpert.core.domain.model.TvDb
import com.example.mysubmissionexpert.core.ui.TvDbAdapter
import com.example.mysubmissionexpert.favorite.databinding.ActivityFavoriteTvDbMainBinding
import com.example.mysubmissionexpert.favorite.di.FavoriteTvDbModule
import com.example.mysubmissionexpert.presentation.ui.detail.DetailTvDbActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteTvDbMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteTvDbMainBinding
    private val favoriteTvDbViewModel: FavoriteTvDbViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteTvDbMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            title = "Favorite Details"
        }

        loadKoinModules(FavoriteTvDbModule().favoriteTvDbModule)
        showFavoriteTvDb()
    }

    private fun showFavoriteTvDb() {
        val favoriteAdapter = TvDbAdapter()
        favoriteAdapter.setOnItemClickTvDbCallback(object : TvDbAdapter.OnItemClickTvDbCallback{
            override fun onItemTvDbClicked(tvDb: TvDb) {
                val intent = Intent(this@FavoriteTvDbMainActivity, DetailTvDbActivity::class.java)
                intent.putExtra(DetailTvDbActivity.EXTRA_DATA, tvDb)
                startActivity(intent)
            }
        })

        favoriteTvDbViewModel.favoriteTvDb.observe(this@FavoriteTvDbMainActivity) {
            favoriteAdapter.setData(it)
        }

        with(binding.rvTvDb) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}