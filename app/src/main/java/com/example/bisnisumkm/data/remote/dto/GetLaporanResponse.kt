package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetLaporanResponse(

	@field:SerializedName("data")
	val dataGetLaporanItem: List<DataGetLaporanItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataGetLaporanItem(

	@field:SerializedName("produsen_name")
	val produsenName: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("qty")
	val qty: String,

	@field:SerializedName("sisa_product")
	val sisaProduct: String,

	@field:SerializedName("laku_product")
	val lakuProduct: String,

	@field:SerializedName("name_toko")
	val nameToko: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("penjual_name")
	val penjualName: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("status")
	val status: String
)