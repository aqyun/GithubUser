package com.dicoding.githubuser.ui
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.FragmentFollowerBinding
import com.dicoding.githubuser.response.FollowerResponseItem
import com.dicoding.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_USERNAME_FOLLOWER = "username_follower"

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val followerUsername = arguments?.getString(ARG_USERNAME_FOLLOWER)
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)
        getFollowers(followerUsername!!)
        return binding.root
    }

    private fun getFollowers(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response<List<FollowerResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserFollowers(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
            }
        })
    }

    private fun setUserFollowers(followers: List<FollowerResponseItem>) {
        val adapter = FollowerAdapter()
        adapter.submitList(followers)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "Follower Fragment"

        fun newInstance(username: String): FollowerFragment {
            val fragment = FollowerFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME_FOLLOWER, username)
            fragment.arguments = args
            return fragment
        }
    }
}
