package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchProdusenResponse(

	@field:SerializedName("data")
	val dataSearchProdusen: DataSearchPrdusen,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataSearchProdusenItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("remember_token")
	val rememberToken: Any,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("number_phone")
	val numberPhone: String
)

data class LinksItem(

	@field:SerializedName("active")
	val active: Boolean,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("url")
	val url: Any
)

data class DataSearchPrdusen(

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("data")
	val dataItem: List<DataSearchProdusenItem>,

	@field:SerializedName("last_page")
	val lastPage: Int,

	@field:SerializedName("next_page_url")
	val nextPageUrl: Any,

	@field:SerializedName("prev_page_url")
	val prevPageUrl: Any,

	@field:SerializedName("first_page_url")
	val firstPageUrl: String,

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("last_page_url")
	val lastPageUrl: String,

	@field:SerializedName("from")
	val from: Int,

	@field:SerializedName("links")
	val links: List<LinksItem>,

	@field:SerializedName("to")
	val to: Int,

	@field:SerializedName("current_page")
	val currentPage: Int
)
