package com.shahzadian.shareimagescreen.domain

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.freenic.ui.model.CoroutineContexts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

interface GetUriToShareUseCase {

    suspend operator fun invoke(bitmap: Bitmap): Uri
}

internal class GetUriToShareUseCaseImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val coroutineContexts: CoroutineContexts,
) : GetUriToShareUseCase {

    override suspend fun invoke(bitmap: Bitmap): Uri {
        val uri = saveBitmapToCache(context, bitmap, "image.png")
        return uri
    }

    private suspend fun saveBitmapToCache(context: Context, bitmap: Bitmap, fileName: String): Uri {
        return withContext(coroutineContexts.io) {
            val cacheDir = File(context.cacheDir, "images")
            if (!cacheDir.exists()) cacheDir.mkdirs()
            val file = File(cacheDir, fileName)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
            }
            val authority = context.packageName + ".fileprovider"
            return@withContext FileProvider.getUriForFile(context, authority, file)
        }
    }
}
