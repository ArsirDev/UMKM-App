package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataLogin,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataLogin(

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

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("number_phone")
	val numberPhone: String
)
