package com.padcmyanmar.smtz.wechatredesign.services

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadImageWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object{
        const val DOWNLOAD_IMAGE = "DOWNLOAD_IMAGE"
    }

    override fun doWork(): Result {
        try {
            val downloadImageString = inputData.getString(DOWNLOAD_IMAGE)

            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager  //1. manager

            val downloadUri = Uri.parse(downloadImageString)            //2. Uri
            val request = DownloadManager.Request(downloadUri)          //3. Request

            request.apply {
                setTitle("Title")
                setDescription("Description")
                setMimeType("image/jpeg")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "downloadedImage")
            }

            manager.enqueue(request)

            return Result.success()
        }
        catch (e:Exception){
            return Result.failure()
        }
    }
}