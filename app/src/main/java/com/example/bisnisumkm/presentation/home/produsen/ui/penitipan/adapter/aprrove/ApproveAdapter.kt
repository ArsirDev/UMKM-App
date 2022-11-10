package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.adapter.aprrove

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.GetAllStatusItem
import com.example.bisnisumkm.databinding.PenjualOnProdusenItemLayoutBinding

class ApproveAdapter: RecyclerView.Adapter<ApproveViewHolder>() {

    private var differCallback = object: DiffUtil.ItemCallback<GetAllStatusItem>() {
        override fun areItemsTheSame(
            oldItem: GetAllStatusItem,
            newItem: GetAllStatusItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetAllStatusItem,
            newItem: GetAllStatusItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproveViewHolder {
        return ApproveViewHolder(PenjualOnProdusenItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ApproveViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        fun instance() = ApproveAdapter()
    }
}