package com.starwarsmasterdetails.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.FileProvider
import com.starwarsmasterdetails.R
import com.starwarsmasterdetails.models.People
import java.io.File
import java.io.FileOutputStream
import java.net.URI

class PDFConverter {

    fun createBitmapForPDF(
        context: Context,
        view: View,
        activity: Activity,
    ): Bitmap {
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display?.getRealMetrics(displayMetrics)
            displayMetrics.densityDpi
        } else {
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
            )
        )
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return Bitmap.createScaledBitmap(bitmap, 595, 842, true)
    }

    private fun convertBitmapToPdf(bitmap: Bitmap, context: Context) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0F, 0F, null)
        pdfDocument.finishPage(page)
        val filePath = File(context.getExternalFilesDir(null), "peopleDetailsPdf.pdf")
        pdfDocument.writeTo(FileOutputStream(filePath))
        pdfDocument.close()
        val uri = renderPdf(context, filePath)
        sharePdf(context, uri)
    }

    fun createPdf(
        context: Context,
        activity: Activity,
        view: View
    ) {
        val bitmap = createBitmapForPDF(context, view, activity)
        convertBitmapToPdf(bitmap, activity)
    }


    private fun renderPdf(context: Context, filePath: File): Uri {
        return FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".fileProvider",
            filePath
        )
    }

    private fun sharePdf(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND) //TODO: change to ACTION_SEND
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_STREAM,uri)
//        intent.setPackage("com.whatsapp")
//        intent.setPackage("com.facebook.katana")
//        intent.setPackage("com.twitter.android")
//        intent.setPackage("com.instagram.android")
//        intent.setPackage("com.pinterest")
        intent.setDataAndType(uri, "application/pdf")
        try {
            context.startActivity(Intent.createChooser(intent,"Share to:"))
        } catch (e: ActivityNotFoundException) {

        }
    }

}