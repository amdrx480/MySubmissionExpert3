package com.example.mysubmissionexpert.presentation.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.mysubmissionexpert.R
import com.example.mysubmissionexpert.core.domain.model.TvDb
import com.example.mysubmissionexpert.core.utils.Constanta
import com.example.mysubmissionexpert.databinding.ActivityDetailTvDbBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTvDbActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTvDbBinding
    private val detailTvDbViewModel: DetailTvDbViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            title = "Tv Details"
        }

        val detailTvDb = intent.getParcelableExtra<TvDb>(EXTRA_DATA)
        if (detailTvDb != null) {
            showDetailTvDb(detailTvDb)
        }
    }

    private fun showDetailTvDb(data: TvDb) {
        with(binding){
            supportActionBar?.title = data.original_title
            tvDetailName.text = data.original_title
            tvDetailOverview.text = data.overview
            tvDetailDate.text = data.release_date
            tvDetailScore.text = data.vote_average.toString()
            Glide.with(this@DetailTvDbActivity)
                .load(Constanta.TV_POSTER + data.poster_path)
                .placeholder(R.drawable.loading_cat)
                .into(binding.ivPoster)

            Glide.with(this@DetailTvDbActivity)
                .load(Constanta.TV_BACKDROP + data.backdrop_path)
                .placeholder(R.drawable.loading_cat)
                .fitCenter()
                .into(binding.ivDetailImage)

            var statusFavorite = data.isFavorite
            setStatusFavorite(statusFavorite)
            binding.btnFabFavoriteDetail.setOnClickListener {
                statusFavorite = !statusFavorite
                detailTvDbViewModel.setFavoriteTvDb(data, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.btnFabFavoriteDetail.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.btnFabFavoriteDetail.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }

    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
