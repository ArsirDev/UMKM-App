package com.example.bisnisumkm.util

import android.content.Context
import android.content.SharedPreferences
import com.example.bisnisumkm.util.SESSION.ACCESS_TOKEN
import com.example.bisnisumkm.util.SESSION.ADDRESS
import com.example.bisnisumkm.util.SESSION.EMAIL
import com.example.bisnisumkm.util.SESSION.NAME
import com.example.bisnisumkm.util.SESSION.NUMBER_PHONE
import com.example.bisnisumkm.util.SESSION.STATUS
import com.example.bisnisumkm.util.SESSION.STORE_NAME
import javax.inject.Inject

class SessionManager @Inject constructor(
    context: Context
) {

    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    fun savePenjualLogin(
        nama: String?,
        nama_toko: String?,
        email: String?,
        alamat: String?,
        status: String?,
        number_phone: String?,
        accessToken: String?
    ) {
        editor.putString(NAME, nama)
        editor.putString(STORE_NAME, nama_toko)
        editor.putString(EMAIL, email)
        editor.putString(ADDRESS, alamat)
        editor.putString(STATUS, status)
        editor.putString(NUMBER_PHONE, number_phone)
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.commit()
    }

    fun createAuthSession(
        nama: String?,
        email: String?,
        address: String?,
        status: String?,
        number_phone: String?,
        accessToken: String?
    ) {
        editor.putString(EMAIL, email)
        editor.putString(NAME, nama)
        editor.putString(ADDRESS, address)
        editor.putString(STATUS, status)
        editor.putString(NUMBER_PHONE, number_phone)
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.commit()
    }

    fun logout() {
        editor.remove(NAME)
        editor.remove(STORE_NAME)
        editor.remove(EMAIL)
        editor.remove(ADDRESS)
        editor.remove(STATUS)
        editor.remove(NUMBER_PHONE)
        editor.remove(ACCESS_TOKEN)
        editor.commit()
    }

    val token: String?
        get() = sharedPreferences.getString(
            ACCESS_TOKEN,
            ""
        )

    val Email: String?
        get() = sharedPreferences.getString(
            EMAIL,
            ""
        )

    val Name: String?
        get() = sharedPreferences.getString(
            NAME,
            ""
        )

    val Store_name: String?
        get() = sharedPreferences.getString(
            STORE_NAME,
            ""
        )

    val address: String?
        get() = sharedPreferences.getString(
            ADDRESS,
            ""
        )

    val status: String?
        get() = sharedPreferences.getString(
            STATUS,
            ""
        )

    val phone: String?
        get() = sharedPreferences.getString(
            NUMBER_PHONE,
            ""
        )


    init {
        sharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = sharedPreferences.edit()
    }

    companion object {
        private const val PREF_NAME = "AUTH"
    }
}