package com.dicoding.githubuser.retrofit

import com.dicoding.githubuser.response.DetailUserResponse
import com.dicoding.githubuser.response.FollowerResponseItem
import com.dicoding.githubuser.response.FollowingResponseItem
import com.dicoding.githubuser.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token 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")

    @GET("search/users")
    fun getSearchGithubUser(
        @Query("q") query: String)
    : Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowerResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponseItem>>
}