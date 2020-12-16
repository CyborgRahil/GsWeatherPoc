package com.rahil.poc.mobileUi

import android.app.Activity
import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.android.gms.security.ProviderInstaller
import com.rahil.poc.mobileUi.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject
import javax.net.ssl.SSLContext

class WeatherForeCastApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        ///SSL handshake terminated errors and using Android 4.0 devices, you will
        // need to enable TLS v1.2 explicitly. Android has supported TLS 1.2 since API 16
        // (Android 4.1). You also should ensure you are using the latest
        // OpenSSL by using the ProviderInstaller
        // https://guides.codepath.com/android/Using-OkHttp#enabling-tls-v1-2-on-older-devices
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            var sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (e: Exception) {
            e.printStackTrace();
        }

        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}

@GlideModule
class MyAppGlideModule : AppGlideModule()
