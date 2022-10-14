package com.example.bisnisumkm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val dataRegister: DataRegister,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataRegister(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("token")
	val token: String
)
