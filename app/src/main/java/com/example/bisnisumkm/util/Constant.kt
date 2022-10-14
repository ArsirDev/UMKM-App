package com.example.bisnisumkm.util

object EndPoint {
    const val LOGIN = "api/bisnis-umkm-login"
    const val REGISTER = "api/bisnis-umkm-register"
    const val PENJUAL_LOGIN = "api/bisnis-umkm-login-penjual"
    const val PENJUAL_REGISTER = "api/bisnis-umkm-register-penjual"
    const val ALL_TOKO = "api/bisnis-umkm-get-toko"
    const val SEARCH_PENJUAL = "api/bisnis-umkm-get-penjual"
    const val SEARCH_PRODUSEN = "api/bisnis-umkm-get-produsen"

    const val GET_DETAIL_PRODUSEN_REQUEST = "api/bisnis-umkm-get-detail-produsen-request"
    const val SET_DETAIL_PRODUSEN_REQUEST = "api/bisnis-umkm-set-detail-produsen_request"
    const val GET_ALL_PRODUSEN_REQUEST = "api/bisnis-umkm-get-all-detail_produsen-request"
    const val GET_SEPESIFICT_DETAIL_PRODUSEN_REQUEST = "api/bisnis-umkm-get-specific-detail_produsen-request"
    const val UPDATE_PRODUSEN_REQUEST = "api/bisnis-umkm-update-detail-produsen-request"
    const val DELETE_PRODUSEN_REQUEST = "api/bisnis-umkm-delete-produsen-request"

    const val SET_LAPORAN = "api/bisnis-umkm-set-laporan"
    const val GET_LAPORAN_PRODUSEN = "api/bisnis-umkm-get-laporan-produsen"
    const val GET_LAPORAN_PENJUAL = "api/bisnis-umkm-get-laporan-penjual"
}

object AUTH {
    const val AUTH_HEADER = "Authorization"
}

object SESSION {
    const val ID = "ID"
    const val EMAIL = "EMAIL"
    const val NAME = "NAME"
    const val STORE_NAME = "STORE_NAME"
    const val ADDRESS = "ADDRESS"
    const val STATUS = "STATUS"
    const val NUMBER_PHONE = "NUMBER_PHONE"
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val ADMIN_LOGIN = "ADMIN_LOGIN"
    const val PRODUSEN_LOGIN = "PRODUSEN_LOGIN"
}

object MESSAGE {
    const val STATUS_SUCCESS = "success"
    const val STATUS_ERROR = "error"
}