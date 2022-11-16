package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.adapter.pending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.GetAllStatusItem
import com.example.bisnisumkm.databinding.PenjualOnProdusenItemLayoutBinding
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce

class PendingAdapter: RecyclerView.Adapter<PendingViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        return PendingViewHolder(PenjualOnProdusenItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
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
        fun instance() = PendingAdapter()
    }
}