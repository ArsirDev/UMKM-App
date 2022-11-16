package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetLaporanResponse(

	@field:SerializedName("data")
	val data: List<DataGetLaporanItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataGetLaporanItem(

	@field:SerializedName("laku_product")
	val lakuProduct: String,

	@field:SerializedName("produsen_name")
	val produsenName: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("penjual_name")
	val penjualName: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("keuntungan_produsen")
	val keuntunganProdusen: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("qty")
	val qty: String,

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("sisa_product")
	val sisaProduct: String,

	@field:SerializedName("barang_rusak")
	val barangRusak: String,

	@field:SerializedName("expired")
	val expired: String,

	@field:SerializedName("name_toko")
	val nameToko: String,

	@field:SerializedName("tanggal_nitip")
	val tanggalNitip: String,

	@field:SerializedName("tanggal_pengambilan")
	val tanggalPengambilan: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: String
)
