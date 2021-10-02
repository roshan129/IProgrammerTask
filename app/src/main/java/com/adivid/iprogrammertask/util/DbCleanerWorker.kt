package com.adivid.iprogrammertask.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adivid.iprogrammertask.data.database.WeatherDao
import java.lang.Exception

class DbCleanerWorker(
    ctx: Context,
    params: WorkerParameters,
    private val dao: WeatherDao
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return try {
            dao.deleteRecords()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}