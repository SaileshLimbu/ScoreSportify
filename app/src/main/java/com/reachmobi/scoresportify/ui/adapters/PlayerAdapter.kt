package com.reachmobi.scoresportify.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reachmobi.scoresportify.databinding.ItemPlayerBinding
import com.reachmobi.scoresportify.models.Player
import com.reachmobi.scoresportify.utils.ItemClickListener


class PlayerAdapter : ListAdapter<Player, PlayerAdapter.Holder>(DiffUtils()) {

    private var itemClickListener: ItemClickListener? = null

    inner class Holder(
        val binding: ItemPlayerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClicked(adapterPosition)
            }
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.player = getItem(position)
    }

    class DiffUtils : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.idPlayer == newItem.idPlayer
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }

    }
}