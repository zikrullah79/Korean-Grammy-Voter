package org.sister.korean_grammy_voter.data

import com.google.gson.annotations.SerializedName

data class VoteResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jsonrpc")
	val jsonrpc: String? = null
)