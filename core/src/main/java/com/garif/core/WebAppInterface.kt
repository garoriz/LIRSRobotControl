package com.garif.core

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterface(private val context: Context) {
    @JavascriptInterface
    fun saveImage(base64Data: String) {
        try {
            val base64Image = base64Data.substring(base64Data.indexOf(",") + 1)

            val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)

            val fileName = "map_" + System.currentTimeMillis() + ".png"

            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/Maps"
            )

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            if (uri != null) {
                val outputStream = checkNotNull(resolver.openOutputStream(uri))
                outputStream.write(imageBytes)
                outputStream.close()

                Toast.makeText(context, "Изображение сохранено: $fileName", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(context, "Ошибка: не удалось сохранить файл", Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Ошибка сохранения: " + e.message, Toast.LENGTH_LONG).show()
        }
    }
}
