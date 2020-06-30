package com.baturin.goldenmonkey

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.a_wv.*
import java.lang.Exception

class WVA: BaseWVA(){

    private val mCC = MCC(this)
    private lateinit var mWVC: MVWC
    private lateinit var sp: SharedPreferences
    private var fbl = "default"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_wv)
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE)
        fbl = sp.getString("deeplink", "default")!!
        if (sp.getString("deeplink", "default")=="default"){
            FacebookSdk.setAutoInitEnabled(true)
            FacebookSdk.fullyInitialize()
            fbRX(savedInstanceState==null)
        } else lU(savedInstanceState==null)
        if (savedInstanceState!=null) wv.restoreState(savedInstanceState)
        mWVC = MVWC(sp)
        wv.webChromeClient = mCC
        wv.webViewClient = mWVC
        wv.addJavascriptInterface(JSI(), "af")

    }

    private fun lU(ins: Boolean){
        wv.post{kotlin.run { wv.loadUrl("https://cardcolor.000webhostapp.com/") }}
        if (ins){
            if (sp.getBoolean("fr", false) && !sp.getBoolean("red", false)){
                startActivity(Intent(this, MA::class.java))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        wv.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        wv.restoreState(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCC.getFP().onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent))
    }

    private fun fb(): Observable<String>{
        return Observable.create { emmiter ->
            try {
                AppLinkData.fetchDeferredAppLinkData(this){ appLinkData ->
                    if (appLinkData!=null && appLinkData.targetUri.toString().contains("golden://monkey/")){
                        emmiter.onNext(appLinkData.targetUri.toString())
                    } else emmiter.onNext("default")
                }
            } catch (e: Exception){
                emmiter.onNext("error")
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun fbRX(ins: Boolean){
        fb().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe { dl ->
            sp.edit().putString("deeplink", gL(dl)).apply()
            lU(ins)
        }
    }

    fun gtC(){
        startActivity(Intent(this, MA::class.java))
        sp.edit().putBoolean("fr", true).apply()
    }

    private fun  gL(s: String): String? {
        var ans = ""
        if (s.length>15)
            for (i in 16 until s.length)
                ans+= s
        else
            ans = s
        return ans
    }

    inner class JSI internal  constructor(){
        @JavascriptInterface fun changeAct(){ gtC() }
    }
}