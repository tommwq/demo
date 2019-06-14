package com.tq.bluetoothdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.bluetooth.*
import androidx.lifecycle.*
import android.content.*
import android.app.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

val BluetoothMessage = "BLUETOOTHMESSAGE"

class MainActivity : AppCompatActivity() {

    private val receiver = BluetoothActionReceiver()

    private lateinit var message: TextView

    fun sendNotice(text: String) {
        CoroutineScope(Main + Job()).launch {
            Repository.instance.insertNotice(Notice(null, System.currentTimeMillis(), text))
        }

        // val newText = "${message.text}\n${text}"
        // message.text = newText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppDatabase.initialize(this)
        Repository.initialize(AppDatabase.instance)

        message = findViewById<TextView>(R.id.message);
        val testSupportButton = findViewById<Button >(R.id.test_support_bluetooth);
        val testActiveButton = findViewById<Button >(R.id.test_active_bluetooth);
        val openButton = findViewById<Button >(R.id.open_bluetooth);
        val closeButton = findViewById<Button >(R.id.close_bluetooth);

        findViewById<Button >(R.id.close_bluetooth).setOnClickListener {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val text = when {
                adapter == null -> "错误：设备不支持蓝牙"
                !adapter.isEnabled() -> "错误：蓝牙未启动"
                else -> {
                    adapter.startDiscovery()
                    "异步：扫描蓝牙"
                }
            }

            sendNotice(text)
        }
        
        openButton.setOnClickListener {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val text = when {
                adapter == null -> "错误：设备不支持蓝牙"
                adapter.isEnabled() -> "错误：蓝牙已启动"
                else -> {
                    "异步：请求打开蓝牙"
                }
            }

            sendNotice(text)
        }

        testSupportButton.setOnClickListener {
            val text = if (BluetoothAdapter.getDefaultAdapter() == null) "设备不支持蓝牙" else "设备支持蓝牙"
        }

        testActiveButton.setOnClickListener {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val text = when {
                adapter == null -> "错误：设备不支持蓝牙"
                adapter.isEnabled() -> "蓝牙已启动"
                !adapter.isEnabled() -> "蓝牙未启动"
                else -> "异常：未知错误"
            }
            sendNotice(text)
        }

        closeButton.setOnClickListener {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val text = when {
                adapter == null -> "错误：设备不支持蓝牙"
                !adapter.isEnabled() -> "错误：蓝牙未启动"
                adapter.disable() -> "请求关闭蓝牙"
                else -> "异常：未知错误"
            }
            sendNotice(text)
        }

        arrayOf(
            BluetoothAdapter.ACTION_STATE_CHANGED,
            BluetoothDevice.ACTION_FOUND
        ).forEach {
            registerReceiver(receiver, IntentFilter(it))
        }


        Repository.instance.getNotices().observe(this, Observer {
            message.text = it.fold("",  {text, line -> "$text\n$line"})
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val text = when (resultCode) {
            RESULT_OK -> "蓝牙已打开"
            RESULT_CANCELED -> "用户拒绝打开蓝牙"
            else -> "异常：无法识别的结果代码 $resultCode"
        }
        sendNotice(text)
    }

    override fun onResume() {
        super.onResume()
        
        if (intent.hasExtra(BluetoothMessage)) {
            sendNotice(intent.getStringExtra(BluetoothMessage))
        }

        setIntent(Intent())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
