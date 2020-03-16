package org.sister.korean_grammy_voter.data

import com.google.gson.annotations.SerializedName

data class Result(

	@field:SerializedName("p")
	val P: P? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)