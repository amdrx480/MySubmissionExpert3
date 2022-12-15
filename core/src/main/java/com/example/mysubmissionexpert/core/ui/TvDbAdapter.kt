package com.example.mysubmissionexpert.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysubmissionexpert.core.R
import com.example.mysubmissionexpert.core.databinding.ItemListTvDbBinding
import com.example.mysubmissionexpert.core.domain.model.TvDb
import com.example.mysubmissionexpert.core.utils.Constanta
import java.util.ArrayList

class TvDbAdapter : RecyclerView.Adapter<TvDbAdapter.ListViewHolder>() {

    private var listData = ArrayList<TvDb>()
    private var onItemClickTvDbCallback : OnItemClickTvDbCallback? = null

    fun setOnItemClickTvDbCallback(onItemClickTvDbCallback: OnItemClickTvDbCallback) {
        this.onItemClickTvDbCallback = onItemClickTvDbCallback
    }

    fun setData(newListData: List<TvDb>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
        Log.d("TAG", "cek item masuk ${listData.toString()}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder {
        val itemListBinding = ItemListTvDbBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemListBinding)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemListTvDbBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TvDb) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(Constanta.TV_POSTER + data.poster_path)
                    .placeholder(R.drawable.loading_cat)
                    .into(ivItemImage)
                tvItemTitle.text = data.original_title
            }
            binding.root.setOnClickListener {
                onItemClickTvDbCallback?.onItemTvDbClicked(data)
            }
        }
    }
    interface OnItemClickTvDbCallback {
        fun onItemTvDbClicked(tvDb: TvDb)
    }
}