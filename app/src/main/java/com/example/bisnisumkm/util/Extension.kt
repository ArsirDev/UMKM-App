package com.example.bisnisumkm.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.bisnisumkm.R
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.MESSAGE.STATUS_SUCCESS
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun Activity.SimpleName() = this::class.java.simpleName

fun Fragment.SimpleName() = this::class.java.simpleName

fun P_E_M(tag: String, message: String) {
    Log.e(tag, message)
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.removeView() {
    this.visibility = View.GONE
}

fun at(at: String) = "Bearer $at"

fun ImageView.loadImage(imageUrl: String, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.NONE) {
    Glide.with(this.context)
        .load(imageUrl)
        .override(480, 320)
        .timeout(20000)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(cacheStrategy)
        .error(ContextCompat.getDrawable(this.context, R.drawable.ic_broken_image_black))
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_image_black))
        .into(this)
}

fun snackbar(view: View, message: String, type: String, duration: Int = Snackbar.LENGTH_SHORT) {
    when(type) {
        STATUS_SUCCESS -> {
            Snackbar.make(view, message, duration).apply {
                this.view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.green_color))
            }.show()
        }
        STATUS_ERROR -> {
            Snackbar.make(view, message, duration).apply {
                this.view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.red_color))
            }.show()
        }
    }
}

private fun getFileExtension(context: Context, uri: Uri): String? {
    val fileType: String? = context.contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
}

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}

fun Context.getFileFromContentUri(contentUri: Uri): File {
    val fileExtension = getFileExtension(this, contentUri)
    val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

    val tempFile = File(this.cacheDir, fileName)
    tempFile.createNewFile()

    var oStream: FileOutputStream? = null
    var inputStream: InputStream? = null

    try {
        oStream = FileOutputStream(tempFile)
        inputStream = this.contentResolver.openInputStream(contentUri)

        inputStream?.let { copy(inputStream, oStream) }
        oStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {

        inputStream?.close()
        oStream?.close()
    }

    return tempFile
}

fun String.removeQuote(): String {
    return this.replace("\"", "\\\"")
}

inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}

fun Any.toJson(): String {
    return Gson().toJson(this)
}

fun Any.toJson(type: Type): String {
    return Gson().toJson(this, type)
}

fun View.setOnClickListenerWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

class MarginItemDecorationVertical(private val spaceHeight: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            bottom = spaceHeight
        }
    }
}

fun String.convertDate(): String {
    val inputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
    val outputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val date: LocalDate = LocalDate.parse(this, inputFormatter)
    return outputFormatter.format(date)
}
