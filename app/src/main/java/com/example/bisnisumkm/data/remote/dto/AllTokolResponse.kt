package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AllTokolResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("nama_toko")
	val namaToko: String
)
