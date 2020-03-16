package org.sister.korean_grammy_voter.data

import com.google.gson.annotations.SerializedName

data class DataItem(

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nominations")
	val nominations: List<NominationsItem?>? = null
)