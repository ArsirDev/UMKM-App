package com.example.bisnisumkm.data.repository

import com.example.bisnisumkm.data.remote.api.ApiInterface
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
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.ResponseHandler
import com.example.bisnisumkm.util.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val apiService: ApiInterface,
    private val responseHandler: ResponseHandler
) : AppsRepository {

    override suspend fun getLogin(email: String, password: String): Result<LoginResponse> =
        responseHandler.handleResponse {
            apiService.getLogin(
                email,
                password
            )
        }

    override suspend fun getRegister(
        name: String,
        email: String,
        alamat: String,
        status: String,
        number_phone: String,
        password: String,
        password_confirmation: String
    ): Result<RegisterResponse> = responseHandler.handleResponse {
        apiService.getRegister(
            name, email, alamat, status, number_phone, password, password_confirmation
        )
    }

    override suspend fun getPenjualLogin(
        nama_toko: String,
        password: String
    ): Result<LoginPenjualResponse> = responseHandler.handleResponse {
        apiService.getPenjualLogin(
            nama_toko,
            password
        )
    }

    override suspend fun getAllToko(): Result<AllTokolResponse> = responseHandler.handleResponse {
        apiService.getAllToko()
    }

    override suspend fun getPenjualRegister(
        name: String,
        email: String,
        nama_toko: String,
        alamat: String,
        number_phone: String,
        status: String,
        image: File,
        password: String,
        password_confirmation: String
    ): Result<RegisterResponse> = responseHandler.handleResponse {
        val requestBody = image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("image", image.name, requestBody)

        val nameRequest = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailRequest = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val nama_tokoRequest = nama_toko.toRequestBody("text/plain".toMediaTypeOrNull())
        val alamatRequest = alamat.toRequestBody("text/plain".toMediaTypeOrNull())
        val number_phoneRequest = number_phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val statusRequest = status.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordRequest = password.toRequestBody("text/plain".toMediaTypeOrNull())
        val password_confirmationRequest =
            password_confirmation.toRequestBody("text/plain".toMediaTypeOrNull())

        apiService.getDataPenjual(
            nameRequest,
            emailRequest,
            nama_tokoRequest,
            alamatRequest,
            number_phoneRequest,
            statusRequest,
            multipartBody,
            passwordRequest,
            password_confirmationRequest
        )
    }

    override suspend fun getSearchPenjual(search: String): Result<SearchPenjualResponse> =
        responseHandler.handleResponse {
            apiService.getSearchPenjual(search)
        }

    override suspend fun getSearchProdusen(search: String): Result<SearchProdusenResponse> =
        responseHandler.handleResponse {
            apiService.getSearchProdusen(search)
        }

    override suspend fun getDetailPenjual(id: Int): Result<DetailPenjualResponse> =
        responseHandler.handleResponse {
            apiService.getDetailPenjual(id)
        }

    override suspend fun setDetailProdusenRequest(
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
        status_penitipan: String
    ): Result<SetRequestProdusenResponse> = responseHandler.handleResponse {
        val requestBodyProdusen =
            image_produsen.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBodyProdusen = MultipartBody.Part.createFormData(
            "image_produsen",
            image_produsen.name,
            requestBodyProdusen
        )

        val id_penjualRequest = id_penjual.toRequestBody("text/plain".toMediaTypeOrNull())
        val id_produsenRequest = id_produsen.toRequestBody("text/plain".toMediaTypeOrNull())
        val name_penjualRequest = name_penjual.toRequestBody("text/plain".toMediaTypeOrNull())
        val name_produsenRequest = name_produsen.toRequestBody("text/plain".toMediaTypeOrNull())
        val name_tokoRequest = name_toko.toRequestBody("text/plain".toMediaTypeOrNull())
        val product_nameRequest = product_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val email_produsenRequest = email_produsen.toRequestBody("text/plain".toMediaTypeOrNull())
        val alamat_produsenRequest = alamat_produsen.toRequestBody("text/plain".toMediaTypeOrNull())
        val alamat_penjualRequest = alamat_penjual.toRequestBody("text/plain".toMediaTypeOrNull())
        val number_phone_produsenRequest =
            number_phone_produsen.toRequestBody("text/plain".toMediaTypeOrNull())
        val number_phone_penjualRequest =
            number_phone_penjual.toRequestBody("text/plain".toMediaTypeOrNull())
        val qtyRequest = qty.toRequestBody("text/plain".toMediaTypeOrNull())
        val hargaRequest = harga.toRequestBody("text/plain".toMediaTypeOrNull())
        val image_penjualRequest = image_penjual.toRequestBody("text/plain".toMediaTypeOrNull())
        val status_penitipanRequest =
            status_penitipan.toRequestBody("text/plain".toMediaTypeOrNull())

        apiService.setDetailProdusenRequest(
            id_penjualRequest,
            id_produsenRequest,
            name_penjualRequest,
            name_produsenRequest,
            name_tokoRequest,
            product_nameRequest,
            email_produsenRequest,
            alamat_produsenRequest,
            alamat_penjualRequest,
            number_phone_produsenRequest,
            number_phone_penjualRequest,
            qtyRequest,
            hargaRequest,
            multipartBodyProdusen,
            image_penjualRequest,
            status_penitipanRequest
        )
    }

    override suspend fun getAllDetailRequestProdusen(name_toko: String): Result<GetAllDetailProdusenRequestResponse> =
        responseHandler.handleResponse {
            apiService.getAllDetailRequestProdusen(name_toko)
        }

    override suspend fun getSprcificDetailRequestProdusen(id: Int): Result<GetSpesificDetailProdusenRequestResponse> =
        responseHandler.handleResponse {
            apiService.getSprcificDetailRequestProdusen(id)
        }

    override suspend fun updateStatusRequest(
        id: Int,
        status_penitipan: String
    ): Result<GeneralResponse> = responseHandler.handleResponse {
        apiService.updateStatusProdusenRequest(
            id,
            status_penitipan
        )
    }

    override suspend fun setLaporan(
        produsen_name: String,
        penjual_name: String,
        product_name: String,
        name_toko: String,
        qty: String,
        sisa_product: String,
        laku_product: String,
        status: String
    ): Result<LaporanResponse> = responseHandler.handleResponse {
        val produsen_nameRequest = produsen_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val penjual_nameRequest = penjual_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val product_nameRequest = product_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val name_tokoRequest = name_toko.toRequestBody("text/plain".toMediaTypeOrNull())
        val qtyRequest = qty.toRequestBody("text/plain".toMediaTypeOrNull())
        val sisa_productRequest = sisa_product.toRequestBody("text/plain".toMediaTypeOrNull())
        val laku_productRequest = laku_product.toRequestBody("text/plain".toMediaTypeOrNull())
        val statusRequest = status.toRequestBody("text/plain".toMediaTypeOrNull())

        apiService.setLaporan(
            produsen_nameRequest,
            penjual_nameRequest,
            product_nameRequest,
            name_tokoRequest,
            qtyRequest,
            sisa_productRequest,
            laku_productRequest,
            statusRequest
        )
    }

    override suspend fun getLaporanProdusen(produsen_name: String): Result<GetLaporanResponse> = responseHandler.handleResponse {
        apiService.getLaporanProdusen(produsen_name)
    }

    override suspend fun getLaporanPenjual(penjual_name: String): Result<GetLaporanResponse> = responseHandler.handleResponse {
        apiService.getLaporanPenjual(penjual_name)
    }

    override suspend fun deleteProdusenRequest(id: Int): Result<GeneralResponse> = responseHandler.handleResponse {
        apiService.deleteProdusenRequest(id)
    }

}