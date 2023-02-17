package com.padcmyanmar.smtz.wechatredesign.views.dialogs

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import com.padcmyanmar.smtz.wechatredesign.R
import kotlinx.android.synthetic.main.dialog_qr_code.view.*


class QRCodeDialogFragment(private val image: Bitmap) : DialogFragment(){

    companion object {
        const val TAG_SHOW_BIG_IMAGE = "TAG_SHOW_BIG_IMAGE"
//        const val EXTRA_IMAGE = "EXTRA_IMAGE"
//
//        fun newFragment(): QRCodeDialogFragment {
//            return QRCodeDialogFragment()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.ivQrCodeDialog?.setImageBitmap(image)
//        dismiss()
    }

    override fun onResume() {
        super.onResume()

//        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
//        params.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
//        params.height = LinearLayoutCompat.LayoutParams.MATCH_PARENT
//        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}