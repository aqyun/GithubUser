package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private var username: String = ""

    fun setUsername(username: String) {
        this.username = username
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowerFragment.newInstance(username)
            1 -> FollowingFragment.newInstance(username)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}

