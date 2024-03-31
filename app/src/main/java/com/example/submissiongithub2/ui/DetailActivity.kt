package com.example.submissiongithub2.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissiongithub2.R
import com.example.submissiongithub2.adapter.SectionsPagerAdapter
import com.example.submissiongithub2.data.database.FavoriteUser
import com.example.submissiongithub2.databinding.ActivityDetailUserBinding
import com.example.submissiongithub2.viewmodel.DetailViewModel
import com.example.submissiongithub2.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        const val EXTRA_USERNAME = "USERNAME"
        const val EXTRA_AVATAR ="AVATAR"
    }


    private lateinit var detailViewModel : DetailViewModel

    private lateinit var binding: ActivityDetailUserBinding


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val getUsername = intent.getStringExtra(EXTRA_USERNAME)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getUsername != null) {
            val sectionPagerAdapter = SectionsPagerAdapter(this, getUsername)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tab)

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        detailViewModel = obtainViewModel(this@DetailActivity)

        var favorUser: FavoriteUser = FavoriteUser().apply {

            if (getUsername != null) {
                this.username = getUsername
            }
            this.avatarUrl = avatar

        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.isFavorite.observe(this){
            setIfFavorite(it)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

        if (getUsername != null) {
            detailViewModel.getDetailUser(getUsername)
        }


        detailViewModel.detailUser.observe(this) {
            if (it != null) {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imgUserDetail)
                binding.tvName.text = it.name
                binding.tvUsername.text = it.login
                binding.tvFollower.text = "${it.followers} Follower"
                binding.tvFollowing.text = "${it.following} Following"
            }
        }

        detailViewModel.getListFav().observe(this) {favoritedUsers ->
            favoritedUsers?.forEach {
                if (it.username == getUsername) {
                    detailViewModel.setIsFavorite(true)
                    favorUser = it
                }
            }
        }

        binding.fabFavorite?.setOnClickListener {
            detailViewModel.updateFavUser(favorUser, this)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setIfFavorite(ifFavorite: Boolean){
        if(ifFavorite){
            binding.fabFavorite?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.favorite))
        }else{
            binding.fabFavorite?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.favorite_item))
        }
    }


    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }
}