package com.example.livelaughlove.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.livelaughlove.model.models.Plant
import com.example.livelaughlove.databinding.RvDiscoverMainItemBinding


class AllPlantsAdapter : RecyclerView.Adapter<AllPlantsAdapter.MainViewHolder>() {


    inner class MainViewHolder(val binding: RvDiscoverMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Plant) {
            binding.apply {
                Glide.with(root.context).load(model.plantPhoto?.first()).into(ivMainItem)
                tvItemTitle.text = model.plantName
                tvMainItemDesc.text = model.plantSoil

                btnMainItemNavigate.setOnClickListener {
                    onItemClickListenerMainItem?.invoke(model)
                }
                root.setOnClickListener {
                    onItemClickListenerMainItem?.invoke(model)

                }

            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.plantPhoto == newItem.plantPhoto
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RvDiscoverMainItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(view)

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)

    }

    override fun getItemCount(): Int = differ.currentList.size


    private var onItemClickListenerMainItem: ((Plant) -> Unit)? = null

    fun setOnItemClickListenerMain(listener: (Plant) -> Unit) {
        onItemClickListenerMainItem = listener
    }


    private var onItemClickListenerForSave: ((Plant) -> Unit)? = null

    fun setOnItemClickListenerSave(listener: (Plant) -> Unit) {
        onItemClickListenerForSave = listener
    }


}
