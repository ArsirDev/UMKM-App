package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchPenjualResponse(

	@field:SerializedName("data")
	val dataSearchPenjual: DataSearchPenjual,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataSearchPenjual(

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("data")
	val dataSearchPenjualItem: List<DataSearchPenjualItem>,

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
	val links: List<LinksSearchPenjualItem>,

	@field:SerializedName("to")
	val to: Int,

	@field:SerializedName("current_page")
	val currentPage: Int
)

data class LinksSearchPenjualItem(

	@field:SerializedName("active")
	val active: Boolean,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("url")
	val url: Any
)

data class DataSearchPenjualItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("nama_toko")
	val namaToko: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("number_phone")
	val numberPhone: String
)
