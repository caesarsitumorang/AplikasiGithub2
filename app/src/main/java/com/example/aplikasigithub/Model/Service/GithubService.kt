package com.example.aplikasigithub.Model.Service

import com.example.aplikasigithub.Model.ItemsItem
import com.example.aplikasigithub.Model.ResponseDetailUser
import com.example.aplikasigithub.Model.ResponseUser
import de.hdodenhof.circleimageview.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GithubService {

    @JvmSuppressWildcards
    @Headers("Authorization: token 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 ")
    @GET("users")
   suspend fun getUserGithub() : MutableList<ItemsItem>

    @JvmSuppressWildcards
    @Headers("Authorization: token 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 ")
    @GET("users/{username}")
    suspend fun getDetailUserGithub(@Path("username") username : String) : ResponseDetailUser

    @JvmSuppressWildcards
    @Headers("Authorization: token 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 ")
    @GET("users/{username}/followers")
    suspend fun getFollowerslUserGithub(@Path("username") username : String) : MutableList<ItemsItem>

    @JvmSuppressWildcards
    @Headers("Authorization: token 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 ")
    @GET("users/{username}/following")
    suspend fun getFollowinglUserGithub(@Path("username") username : String) : MutableList<ItemsItem>

    @JvmSuppressWildcards
    @Headers("Authorization: token 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 ")
    @GET("search/users")
    suspend fun SearchlUserGithub(@QueryMap params: Map<String, Any>) : ResponseUser
}