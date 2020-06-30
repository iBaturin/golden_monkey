package com.baturin.goldenmonkey

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat

@Suppress("DEPRECATION")
class MCC (_a: Activity): WebChromeClient(){

    private val act = _a
    private lateinit var fpc: ValueCallback<Array<Uri>>
    private val REQ = 1000

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        fpc = filePathCallback!!
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        i.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
        ActivityCompat.startActivityForResult(act, i, REQ, Bundle())
        return true
    }

    fun getFP(): ValueCallback<Array<Uri>> {
        return fpc
    }

    private var mCV: View? = null
    private var mCVC: CustomViewCallback? = null
    private var mOO = 0
    private var mOSUV = 0

    override fun getDefaultVideoPoster(): Bitmap? {
        if (mCV != null) return BitmapFactory.decodeResource(act.resources, 2130837573)
        return mCV
    }

    override fun onHideCustomView() {
        (act.window.decorView as FrameLayout).removeView(mCV)
        mCV = null
        act.window.decorView.systemUiVisibility = mOSUV
        act.requestedOrientation = mOO
        mCVC!!.onCustomViewHidden()
        mCVC = null
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        if (mCV!=null){
            onHideCustomView()
            return
        }
        mCV = view
        mOSUV = act.window.decorView.systemUiVisibility
        mOO = act.requestedOrientation
        mCVC = callback
        (act.window.decorView as FrameLayout).addView(mCV, FrameLayout.LayoutParams(-1,-1))
        act.window.decorView.systemUiVisibility = 3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}