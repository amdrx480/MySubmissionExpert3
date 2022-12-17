package com.example.mysubmissionexpert.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.mysubmissionexpert.core.domain.model.TvDb

class TvDbDiffCallback(
    private val mOldContentList: List<TvDb>,
    private val mNewContentList: List<TvDb>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldContentList.size
    }

    override fun getNewListSize(): Int {
        return mNewContentList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldContentList[oldItemPosition].id  == mNewContentList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContent: TvDb = mOldContentList[oldItemPosition]
        val newContent: TvDb = mNewContentList[newItemPosition]
        return oldContent.original_title == newContent.original_title
    }
}