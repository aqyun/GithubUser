package com.dicoding.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.response.DetailUserResponse
import com.dicoding.githubuser.retrofit.ApiConfig
import com.dicoding.githubuser.helper.ViewModelFactories
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var viewModelFactories: ViewModelFactories

    companion object {
        private const val TAG = "DetailUserActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: "DefaultUsername"
        val avatarUrl = intent.getStringExtra("avatarUrl") ?: "DefaultAvatarUrl"

        val apiService = ApiConfig.getApiService()

        viewModelFactories = ViewModelFactories.getInstance(application)
        detailUserViewModel = ViewModelProvider(this, viewModelFactories)[DetailUserViewModel::class.java]

        showLoading(true)
        apiService.getDetailUser(username).enqueue(object : Callback<DetailUserResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val detailUserResponse = response.body()

                    val name = detailUserResponse?.name
                    val followersCount = detailUserResponse?.followers ?: 0
                    val followingCount = detailUserResponse?.following ?: 0

                    binding.usernameTextView.text = username
                    binding.nameTextView.text = name
                    binding.followersCountTextView.text = "$followersCount Followers"
                    binding.followingCountTextView.text = "$followingCount Following"

                    Glide.with(this@DetailUserActivity)
                        .load(avatarUrl)
                        .into(binding.avatarImageView)
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

        val favoriteButton = binding.fabFavorite

        detailUserViewModel.getFavoriteUserByUsername(username).observe(this) { favoriteUser ->
            if (favoriteUser != null) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_filled)
                favoriteButton.setOnClickListener {
                    detailUserViewModel.delete(favoriteUser)
                    favoriteButton.setImageResource(R.drawable.ic_favorite)
                }
            } else {
                favoriteButton.setImageResource(R.drawable.ic_favorite)
                favoriteButton.setOnClickListener {
                    val newFavoriteUser = FavoriteUser(username = username, avatarUrl = avatarUrl)
                    detailUserViewModel.insert(newFavoriteUser)
                    favoriteButton.setImageResource(R.drawable.ic_favorite_filled) // Set ikon menjadi favorit
                }
            }
        }

        favoriteButton.setOnClickListener {
            val favoriteUser = FavoriteUser(username = username, avatarUrl = avatarUrl)
            val isFavorite = detailUserViewModel.getFavoriteUserByUsername(username).value != null

            if (isFavorite) {
                detailUserViewModel.delete(favoriteUser)
            } else {
                detailUserViewModel.insert(favoriteUser)
            }
        }

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.setUsername(username)

        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
