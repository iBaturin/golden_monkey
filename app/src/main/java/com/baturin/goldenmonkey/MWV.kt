package com.baturin.goldenmonkey

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView

@SuppressLint("SetJavaScriptEnabled")
class MWV  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): WebView(context, attrs, defStyleAttr){


    init {
        this.scrollBarSize = View.SCROLLBARS_OUTSIDE_OVERLAY
        val cookie: CookieManager = CookieManager.getInstance()
        cookie.setAcceptCookie(true)
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        this.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        this.settings.javaScriptCanOpenWindowsAutomatically = true
        this.settings.allowContentAccess = true
        this.settings.allowFileAccessFromFileURLs = true
        this.settings.allowUniversalAccessFromFileURLs = true
        this.settings.javaScriptEnabled = true
        this.settings.loadWithOverviewMode = true
        this.settings.allowFileAccess = true
        this.settings.domStorageEnabled = true
        this.settings.databaseEnabled = true
        this.settings.useWideViewPort = true
        this.settings.supportZoom()
        this.settings.setAppCacheEnabled(true)

        this.canGoBack()
        this.setOnKeyListener(OnKeyListener { view, i, keyEvent ->
            if ((i == KeyEvent.KEYCODE_BACK) && (keyEvent.action == MotionEvent.ACTION_UP) && this.canGoBack()) {
                this.goBack()
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }

}
