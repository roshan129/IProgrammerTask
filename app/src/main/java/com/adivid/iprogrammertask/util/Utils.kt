package com.adivid.iprogrammertask.util

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import android.net.ConnectivityManager

import android.app.Activity




object Utils {

    fun scheduleWorker(context: Context) {
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val refreshCpnWork = PeriodicWorkRequest.Builder(DbCleanerWorker::class.java,
            15, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag("myWorkManager")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager",
            ExistingPeriodicWorkPolicy.REPLACE, refreshCpnWork)
    }

    fun isNetworkConnected(activity: Activity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null &&
                connectivityManager.activeNetworkInfo!!.isConnected
    }

}