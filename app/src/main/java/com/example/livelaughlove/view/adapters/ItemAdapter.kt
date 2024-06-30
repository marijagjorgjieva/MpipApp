package com.example.livelaughlove.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.livelaughlove.databinding.VpDetailItemBinding

class ItemAdapter(val imageList: List<String>?) :
    RecyclerView.Adapter<ItemAdapter.DetailvpVH>() {


    inner class DetailvpVH(val binding: VpDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: String) {
            binding.apply {
                Glide.with(root.context).load(model).into(ivDetailVPItem)
            }
        }
    }

    val differ: AsyncListDiffer<String>
        get() = AsyncListDiffer(/* adapter = */ this, /* diffCallback = */ this.differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailvpVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = VpDetailItemBinding.inflate(inflater, parent, false)
        return DetailvpVH(view)
    }


    override fun onBindViewHolder(holder: DetailvpVH, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = differ.currentList.size
    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

}