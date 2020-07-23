package com.baturin.goldenmonkey

import android.webkit.WebView
import android.webkit.WebViewClient

class MVWC (): WebViewClient(){
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url!!)
        return true
    }
}