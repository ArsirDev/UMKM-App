package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.adapter.aprrove

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.GetAllStatusItem
import com.example.bisnisumkm.databinding.PenjualOnProdusenItemLayoutBinding
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce

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
        holder.apply {
            bind(differ.currentList[position].also { item ->
                itemView.setOnClickListenerWithDebounce {
                    onItemClickListener?.let { dataItem ->
                        dataItem(item)
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener:((GetAllStatusItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (GetAllStatusItem) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        fun instance() = ApproveAdapter()
    }
}