package org.sister.korean_grammy_voter.data

import com.google.gson.annotations.SerializedName

data class Nominations(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)