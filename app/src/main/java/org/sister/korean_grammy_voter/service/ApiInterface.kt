package org.sister.korean_grammy_voter.service

import com.google.gson.JsonObject
import org.sister.korean_grammy_voter.data.VoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    //interface untuk melakukan method voteNominations dengan notasi POST
    @POST("/")
    fun voteNominations(@Body body : JsonObject): Call<VoteResponse>
}