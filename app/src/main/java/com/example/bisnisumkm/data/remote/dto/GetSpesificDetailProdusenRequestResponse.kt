package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetSpesificDetailProdusenRequestResponse(

	@field:SerializedName("data")
	val dataSpesificDetailProdusenRequestResponse: DataSpesificDetailProdusenRequestResponse,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataSpesificDetailProdusenRequestResponse(

	@field:SerializedName("alamat_penjual")
	val alamatPenjual: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id_produsen")
	val idProdusen: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("alamat_produsen")
	val alamatProdusen: String,

	@field:SerializedName("image_penjual")
	val imagePenjual: String,

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("tanggal_pengambilan")
	val tanggalPengambilan: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name_produsen")
	val nameProdusen: String,

	@field:SerializedName("email_produsen")
	val emailProdusen: String,

	@field:SerializedName("status_penitipan")
	val statusPenitipan: String,

	@field:SerializedName("id_penjual")
	val idPenjual: String,

	@field:SerializedName("number_phone_produsen")
	val numberPhoneProdusen: String,

	@field:SerializedName("qty")
	val qty: String,

	@field:SerializedName("name_toko")
	val nameToko: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("number_phone_penjual")
	val numberPhonePenjual: String,

	@field:SerializedName("image_produsen")
	val imageProdusen: String,

	@field:SerializedName("name_penjual")
	val namePenjual: String
)
