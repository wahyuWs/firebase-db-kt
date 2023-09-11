package com.wahyus.firebasedbkt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wahyus.firebasedbkt.databinding.ItemUserBinding

class FetchAdapter(private val itemAdapterCallback: ItemAdapterCallback): RecyclerView.Adapter<FetchAdapter.FetchViewHolder>() {

    private val data = ArrayList<User>()

    fun setData(itemData: List<User>) {
        data.clear()
        data.addAll(itemData)
        notifyDataSetChanged()
    }

    class FetchViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, itemAdapterCallback: ItemAdapterCallback) {
            binding.tvDataName.text = user.name
            binding.tvDataAddress.text = user.address

            itemView.setOnClickListener {
                itemAdapterCallback.onClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FetchViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FetchViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FetchViewHolder, position: Int) {
        holder.bind(data[position], itemAdapterCallback)
    }

    interface ItemAdapterCallback {
        fun onClick(data: User)
    }
}