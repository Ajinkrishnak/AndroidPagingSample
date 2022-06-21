package com.ak.androidpagingsample.data.model

import com.google.gson.annotations.SerializedName

data class PlayersResponse(

	@field:SerializedName("data")
	val data: List<PlayerItem>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class PlayerItem(

	@field:SerializedName("weight_pounds")
	val weightPounds: Any? = null,

	@field:SerializedName("height_feet")
	val heightFeet: Any? = null,

	@field:SerializedName("height_inches")
	val heightInches: Any? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: String? = null,

	@field:SerializedName("team")
	val team: Team? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null
)

data class Meta(

	@field:SerializedName("next_page")
	val nextPage: Int? = null,

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
)

data class Team(

	@field:SerializedName("division")
	val division: String? = null,

	@field:SerializedName("conference")
	val conference: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("abbreviation")
	val abbreviation: String? = null
)
