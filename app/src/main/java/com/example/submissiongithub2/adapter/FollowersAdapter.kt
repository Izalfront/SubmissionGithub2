package com.example.submissiongithub2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissiongithub2.data.response.ItemsItem
import com.example.submissiongithub2.databinding.ListUserBinding
import com.example.submissiongithub2.ui.DetailActivity

class FollowersAdapter : ListAdapter<ItemsItem, FollowersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gituser: ItemsItem){
            binding.tvUser.text = gituser.login
            Glide.with(binding.root)
                .load(gituser.avatarUrl)
                .into(binding.imgUserPhoto)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, gituser.login)
                intent.putExtra(EXTRA_AVATAR, gituser.avatarUrl)
                intent.putExtra(EXTRA_ID, gituser.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
        const val EXTRA_USERNAME = "USERNAME"
        const val EXTRA_AVATAR = "AVATAR"
        const val EXTRA_ID = "ID"
    }
}