package com.dicoding.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.githubuser.response.FollowingResponseItem
import com.dicoding.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_USERNAME_FOLLOWING = "username_following"

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val username = arguments?.getString(ARG_USERNAME_FOLLOWING)
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)
        getFollowing(username!!)
        return binding.root
    }

    private fun getFollowing(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserFollowing(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
            }
        })
    }

    private fun setUserFollowing(following: List<FollowingResponseItem>) {
        val adapter = FollowingAdapter()
        adapter.submitList(following)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "Following Fragment"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME_FOLLOWING, username)
            fragment.arguments = args
            return fragment
        }
    }
}
