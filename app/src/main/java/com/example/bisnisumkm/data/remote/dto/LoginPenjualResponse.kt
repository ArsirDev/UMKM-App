package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginPenjualResponse(

	@field:SerializedName("data")
	val dataLoginPenjual: DataLoginPenjual,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataLoginPenjual(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("nama_toko")
	val namaToko: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("number_phone")
	val numberPhone: String
)
