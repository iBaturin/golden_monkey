package com.baturin.goldenmonkey

import android.content.SharedPreferences
import android.webkit.WebView
import android.webkit.WebViewClient

class MVWC (_pr: SharedPreferences): WebViewClient(){

    private val sp = _pr
    private var fbl = "default"
    private var f = true
    private val aN = "150-android-evgen"

    init {
        f = true
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (f){
            fbl = sp.getString("deepLink", "default")!!
            view?.loadUrl("$url?app=$aN&stream=$fbl")
            f = false
            sp.edit().putBoolean("red", true).apply()
        } else view?.loadUrl("$url")
        return true
    }
}