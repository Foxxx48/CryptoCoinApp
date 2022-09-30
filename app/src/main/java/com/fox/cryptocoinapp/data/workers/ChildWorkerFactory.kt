package com.fox.cryptocoinapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {

    fun crate(
        context: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker
}