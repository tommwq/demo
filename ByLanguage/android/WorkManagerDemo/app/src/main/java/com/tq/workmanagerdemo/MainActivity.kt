package com.tq.workmanagerdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<com.tq.workmanagerdemo.databinding.ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        binding.button.setOnClickListener {

            val data = Data.Builder()
                .putString("input", "ok")
                .build()

            val request = OneTimeWorkRequest.Builder(UpdateWorker::class.java)
                .setInputData(data)
                .build()

            WorkManager.getInstance().enqueue(request)
            WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(this, object : Observer<WorkStatus> {
                    override fun onChanged(t: WorkStatus?) {
                        if (t?.state == State.SUCCEEDED) {
                            binding.text = t.outputData.getString("output", "123")
                        }
                    }
                })
        }
    }
}

class UpdateWorker() : Worker() {
    override fun doWork(): WorkerResult {
        outputData = Data.Builder()
            .putString("output", "haha")
            .build()

        return WorkerResult.SUCCESS
    }
}