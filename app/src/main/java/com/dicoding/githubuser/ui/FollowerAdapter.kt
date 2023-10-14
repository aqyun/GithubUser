package com.dicoding.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemRowFollowBinding
import com.dicoding.githubuser.response.FollowerResponseItem

class FollowerAdapter : ListAdapter<FollowerResponseItem, FollowerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemRowFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowerResponseItem){
            binding.tvUsername.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.ivAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowerResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowerResponseItem, newItem: FollowerResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FollowerResponseItem, newItem: FollowerResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
