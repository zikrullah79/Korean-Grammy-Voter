package org.sister.korean_grammy_voter.data

import com.google.gson.annotations.SerializedName

data class NominationsItem(

	@field:SerializedName("singer")
	val singer: String? = null,

	@field:SerializedName("percentage")
	val percentage: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vote")
	val vote: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)