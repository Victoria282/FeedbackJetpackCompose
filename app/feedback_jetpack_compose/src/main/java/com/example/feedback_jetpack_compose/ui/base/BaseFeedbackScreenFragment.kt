package ru.taxcom.feedback.ui.base

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.viewbinding.ViewBinding
import ru.taxcom.feedback.navigation.InnerNavigator
import ru.taxcom.taxcomkit.ui.basefragment.BaseTaxcomDaggerFragment

abstract class BaseFeedbackScreenFragment<T : ViewBinding> : BaseTaxcomDaggerFragment<T>() {

    internal var innerNavigator: InnerNavigator? = null

    internal fun getImagesFromGallery(context: Context): List<Uri> {
        val images = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        context.contentResolver.query(uri, projection, null, null, sortOrder)?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(uri, id.toString())
                images.add(contentUri)
            }
        }
        return images
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        innerNavigator = context as? InnerNavigator
    }

    override fun onDetach() {
        super.onDetach()
        innerNavigator = null
    }

    companion object {
        internal const val REQUEST_MEDIA_CODE = 100
        internal const val REQUEST_STORAGE_CODE = 200
    }
}