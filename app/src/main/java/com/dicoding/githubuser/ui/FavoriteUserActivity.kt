package com.dicoding.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.githubuser.helper.ViewModelFactories

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite Users"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactories.getInstance(application)
        )[FavoriteUserViewModel::class.java]

        adapter = FavoriteUserAdapter(this)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.getFavoriteUsers().observe(this) { users ->
            adapter.submitList(users)
            viewModel.setLoading(false)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(favoriteUser: FavoriteUser) {
                val intent = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
                intent.putExtra("username", favoriteUser.username)
                intent.putExtra("avatarUrl", favoriteUser.avatarUrl)
                startActivity(intent)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
