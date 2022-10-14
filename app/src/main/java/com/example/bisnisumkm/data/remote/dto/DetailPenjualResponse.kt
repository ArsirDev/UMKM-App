package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailPenjualResponse(

	@field:SerializedName("data")
	val dataDetailPenjual: DataDetailPenjual,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataDetailPenjual(

	@field:SerializedName("alamat_penjual")
	val alamatPenjual: String,

	@field:SerializedName("name_produsen")
	val nameProdusen: String,

	@field:SerializedName("email_produsen")
	val emailProdusen: String,

	@field:SerializedName("id_penjual")
	val idPenjual: Int,

	@field:SerializedName("number_phone_produsen")
	val numberPhoneProdusen: String,

	@field:SerializedName("name_toko")
	val nameToko: String,

	@field:SerializedName("id_produsen")
	val idProdusen: Int,

	@field:SerializedName("number_phone_penjual")
	val numberPhonePenjual: String,

	@field:SerializedName("alamat_produsen")
	val alamatProdusen: String,

	@field:SerializedName("name_penjual")
	val namePenjual: String,

	@field:SerializedName("image")
	val image: String
)
