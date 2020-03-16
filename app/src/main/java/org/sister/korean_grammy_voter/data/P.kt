package org.sister.korean_grammy_voter.data

import com.google.gson.annotations.SerializedName

data class P(

	@field:SerializedName("idnominations")
	val idnominations: Int? = null,

	@field:SerializedName("macaddress")
	val macaddress: String? = null,

	@field:SerializedName("idnominator")
	val idnominator: Int? = null
)