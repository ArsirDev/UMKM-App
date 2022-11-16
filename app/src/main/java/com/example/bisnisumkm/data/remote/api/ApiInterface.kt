package com.example.bisnisumkm.data.remote.api

import com.example.bisnisumkm.data.remote.dto.*
import com.example.bisnisumkm.util.EndPoint.ALL_TOKO
import com.example.bisnisumkm.util.EndPoint.DELETE_LAPORAN_PENJUAL
import com.example.bisnisumkm.util.EndPoint.DELETE_LAPORAN_PRODUSEN
import com.example.bisnisumkm.util.EndPoint.DELETE_PRODUSEN_REQUEST
import com.example.bisnisumkm.util.EndPoint.GET_DETAIL_PRODUSEN_REQUEST
import com.example.bisnisumkm.util.EndPoint.GET_ALL_PRODUSEN_REQUEST
import com.example.bisnisumkm.util.EndPoint.GET_ALL_STATUS_REQUEST
import com.example.bisnisumkm.util.EndPoint.GET_LAPORAN_PENJUAL
import com.example.bisnisumkm.util.EndPoint.GET_LAPORAN_PRODUSEN
import com.example.bisnisumkm.util.EndPoint.GET_SEPESIFICT_DETAIL_PRODUSEN_REQUEST
import com.example.bisnisumkm.util.EndPoint.LOGIN
import com.example.bisnisumkm.util.EndPoint.PENJUAL_LOGIN
import com.example.bisnisumkm.util.EndPoint.PENJUAL_REGISTER
import com.example.bisnisumkm.util.EndPoint.REGISTER
import com.example.bisnisumkm.util.EndPoint.SEARCH_PENJUAL
import com.example.bisnisumkm.util.EndPoint.SEARCH_PRODUSEN
import com.example.bisnisumkm.util.EndPoint.SET_DETAIL_PRODUSEN_REQUEST
import com.example.bisnisumkm.util.EndPoint.SET_LAPORAN
import com.example.bisnisumkm.util.EndPoint.UPDATE_PRODUSEN_REQUEST
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiInterface {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun getLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST(REGISTER)
    suspend fun getRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("alamat") alamat: String,
        @Field("status") status: String,
        @Field("number_phone") number_phone: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST(PENJUAL_LOGIN)
    suspend fun getPenjualLogin(
        @Field("nama_toko") nama_toko: String,
        @Field("password") password: String
    ): Response<LoginPenjualResponse>

    @GET(ALL_TOKO)
    suspend fun getAllToko(): Response<AllTokolResponse>

    @Multipart
    @POST(PENJUAL_REGISTER)
    suspend fun getDataPenjual(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("nama_toko") nama_toko: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("number_phone") number_phone: RequestBody,
        @Part("status") status: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") password_confirmation: RequestBody,
    ): Response<RegisterResponse>

    @GET(SEARCH_PENJUAL)
    suspend fun getSearchPenjual(
        @Query("search") search: String
    ): Response<SearchPenjualResponse>

    @GET(SEARCH_PRODUSEN)
    suspend fun getSearchProdusen(
        @Query("search") search: String
    ): Response<SearchProdusenResponse>

    @GET(GET_DETAIL_PRODUSEN_REQUEST)
    suspend fun getDetailPenjual(
        @Query("id") id: Int
    ): Response<DetailPenjualResponse>

    @Multipart
    @POST(SET_DETAIL_PRODUSEN_REQUEST)
    suspend fun setDetailProdusenRequest(
        @Part("id_penjual") id_penjual: RequestBody,
        @Part("id_produsen") id_produsen: RequestBody,
        @Part("name_penjual") name_penjual: RequestBody,
        @Part("name_produsen") name_produsen: RequestBody,
        @Part("name_toko") name_toko: RequestBody,
        @Part("product_name") product_name: RequestBody,
        @Part("email_produsen") email_produsen: RequestBody,
        @Part("alamat_produsen") alamat_produsen: RequestBody,
        @Part("alamat_penjual") alamat_penjual: RequestBody,
        @Part("number_phone_produsen") number_phone_produsen: RequestBody,
        @Part("number_phone_penjual") number_phone_penjual: RequestBody,
        @Part("tanggal_pengambilan") tanggal_pengambilan: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part image_produsen: MultipartBody.Part,
        @Part("image_penjual") image_penjual: RequestBody,
        @Part("status_penitipan") status_penitipan: RequestBody,
    ): Response<SetRequestProdusenResponse>

    @GET(GET_ALL_PRODUSEN_REQUEST)
    suspend fun getAllDetailRequestProdusen(
        @Query("name_toko") search: String
    ): Response<GetAllDetailProdusenRequestResponse>

    @GET(GET_ALL_STATUS_REQUEST)
    suspend fun getAllStatusRequestProdusen(
        @Query("status_penitipan") status_penitipan: String
    ): Response<GetAllStatusResponse>

    @GET(GET_SEPESIFICT_DETAIL_PRODUSEN_REQUEST)
    suspend fun getSprcificDetailRequestProdusen(
        @Query("id") id: Int
    ): Response<GetSpesificDetailProdusenRequestResponse>

    @GET(UPDATE_PRODUSEN_REQUEST)
    suspend fun updateStatusProdusenRequest(
        @Query("id") id: Int,
        @Query("status_penitipan") status_penitipan: String
    ): Response<GeneralResponse>

    @Multipart
    @POST(SET_LAPORAN)
    suspend fun setLaporan(
        @Part("produsen_name") produsen_name: RequestBody,
        @Part("penjual_name") penjual_name: RequestBody,
        @Part("product_name") product_name: RequestBody,
        @Part("name_toko") name_toko: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("sisa_product") sisa_product: RequestBody,
        @Part("laku_product") laku_product: RequestBody,
        @Part("keuntungan_produsen") keuntungan_produsen: RequestBody,
        @Part("barang_rusak") barang_rusak: RequestBody,
        @Part("expired") expired: RequestBody,
        @Part("tanggal_nitip") tanggal_nitip: RequestBody,
        @Part("tanggal_pengambilan") tanggal_pengambilan: RequestBody,
        @Part("status") status: RequestBody
    ): Response<SetLaporanResponse>

    @GET(GET_LAPORAN_PRODUSEN)
    suspend fun getLaporanProdusen(
        @Query("produsen_name") produsen_name: String,
    ): Response<GetLaporanResponse>

    @GET(GET_LAPORAN_PENJUAL)
    suspend fun getLaporanPenjual(
        @Query("penjual_name") penjual_name: String,
    ): Response<GetLaporanResponse>

    @GET(DELETE_PRODUSEN_REQUEST)
    suspend fun deleteProdusenRequest(
        @Query("id") id: Int
    ): Response<GeneralResponse>

    @GET(DELETE_LAPORAN_PENJUAL)
    suspend fun deleteLaporanPenjualRequest(
        @Query("id") id: Int
    ): Response<GeneralResponse>

    @GET(DELETE_LAPORAN_PRODUSEN)
    suspend fun deleteLaporanProdusenRequest(
        @Query("id") id: Int
    ): Response<GeneralResponse>
}