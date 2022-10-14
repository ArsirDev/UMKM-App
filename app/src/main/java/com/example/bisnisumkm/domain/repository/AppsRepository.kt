package com.example.bisnisumkm.domain.repository

import com.example.bisnisumkm.data.remote.dto.AllTokolResponse
import com.example.bisnisumkm.data.remote.dto.DetailPenjualResponse
import com.example.bisnisumkm.data.remote.dto.GeneralResponse
import com.example.bisnisumkm.data.remote.dto.GetAllDetailProdusenRequestResponse
import com.example.bisnisumkm.data.remote.dto.GetLaporanResponse
import com.example.bisnisumkm.data.remote.dto.GetSpesificDetailProdusenRequestResponse
import com.example.bisnisumkm.data.remote.dto.LaporanResponse
import com.example.bisnisumkm.data.remote.dto.LoginPenjualResponse
import com.example.bisnisumkm.data.remote.dto.LoginResponse
import com.example.bisnisumkm.data.remote.dto.RegisterResponse
import com.example.bisnisumkm.data.remote.dto.SearchPenjualResponse
import com.example.bisnisumkm.data.remote.dto.SearchProdusenResponse
import com.example.bisnisumkm.data.remote.dto.SetRequestProdusenResponse
import com.example.bisnisumkm.util.Result
import java.io.File

interface AppsRepository {

    suspend fun getLogin(
        email: String,
        password: String
    ): Result<LoginResponse>

    suspend fun getRegister(
        name: String,
        email: String,
        alamat: String,
        status: String,
        number_phone: String,
        password: String,
        password_confirmation: String
    ): Result<RegisterResponse>

    suspend fun getPenjualLogin(
        nama_toko: String,
        password: String
    ): Result<LoginPenjualResponse>

    suspend fun getAllToko(): Result<AllTokolResponse>

    suspend fun getPenjualRegister(
        name: String,
        email: String,
        nama_toko: String,
        alamat: String,
        number_phone: String,
        status: String,
        image: File,
        password: String,
        password_confirmation: String
    ): Result<RegisterResponse>

    suspend fun getSearchPenjual(
        search: String
    ): Result<SearchPenjualResponse>

    suspend fun getSearchProdusen(
        search: String
    ): Result<SearchProdusenResponse>

    suspend fun getDetailPenjual(
        id: Int
    ): Result<DetailPenjualResponse>

    suspend fun setDetailProdusenRequest(
        id_penjual: String,
        id_produsen: String,
        name_penjual: String,
        name_produsen: String,
        name_toko: String,
        product_name: String,
        email_produsen: String,
        alamat_produsen: String,
        alamat_penjual: String,
        number_phone_produsen: String,
        number_phone_penjual: String,
        qty: String,
        harga: String,
        image_produsen: File,
        image_penjual: String,
        status_penitipan: String,
    ): Result<SetRequestProdusenResponse>

    suspend fun getAllDetailRequestProdusen(
        name_toko: String
    ): Result<GetAllDetailProdusenRequestResponse>

    suspend fun getSprcificDetailRequestProdusen(
        id: Int
    ): Result<GetSpesificDetailProdusenRequestResponse>

    suspend fun updateStatusRequest(
        id: Int,
        status_penitipan: String
    ): Result<GeneralResponse>

    suspend fun setLaporan(
        produsen_name: String,
        penjual_name: String,
        product_name: String,
        name_toko: String,
        qty: String,
        sisa_product: String,
        laku_product: String,
        status: String
    ): Result<LaporanResponse>

    suspend fun getLaporanProdusen(
        produsen_name: String,
    ): Result<GetLaporanResponse>

    suspend fun getLaporanPenjual(
        penjual_name: String,
    ): Result<GetLaporanResponse>

    suspend fun deleteProdusenRequest(
        id: Int
    ): Result<GeneralResponse>
}