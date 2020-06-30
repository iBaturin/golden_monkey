package com.baturin.goldenmonkey

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.os.Build
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.yandex.metrica.push.YandexMetricaPush

class Base : Application() {

    val yk = "503efd22-bb3d-4650-a7f6-a109813252c3"

    override fun onCreate() {
        super.onCreate()
        YandexMetrica.activate(applicationContext, YandexMetricaConfig.newConfigBuilder(yk).build())
        YandexMetricaPush.init(applicationContext)
        YandexMetrica.enableActivityAutoTracking(this)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            with(YandexMetricaPush.getDefaultNotificationChannel()){
                this?.importance = NotificationManager.IMPORTANCE_DEFAULT
                this?.enableVibration(true)
                this?.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
        }
    }
}