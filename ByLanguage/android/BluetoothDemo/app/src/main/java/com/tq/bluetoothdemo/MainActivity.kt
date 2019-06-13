package com.tq.bluetoothdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.bluetooth.*
import android.content.*
import android.app.*

class MainActivity : AppCompatActivity() {

    private lateinit var message: TextView

    fun appendMessage(text: String) {
        val newText = "${message.text}\n${text}"
        message.text = newText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById<TextView>(R.id.message);
        val testSupportButton = findViewById<Button >(R.id.test_support_bluetooth);
        val testActiveButton = findViewById<Button >(R.id.test_active_bluetooth);
        val openButton = findViewById<Button >(R.id.open_bluetooth);
        val closeButton = findViewById<Button >(R.id.close_bluetooth);

        openButton.setOnClickListener {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val text = when {
                adapter == null -> "错误：设备不支持蓝牙"
                adapter.isEnabled() -> "错误：蓝牙已启动"
                else -> {
                    "异步：请求打开蓝牙"
                }
            }

            appendMessage(text)
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
            appendMessage(text)
        }

        closeButton.setOnClickListener {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val text = when {
                adapter == null -> "错误：设备不支持蓝牙"
                !adapter.isEnabled() -> "错误：蓝牙未启动"
                adapter.disable() -> "请求关闭蓝牙"
                else -> "异常：未知错误"
            }
            appendMessage(text)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val text = when (resultCode) {
            RESULT_OK -> "蓝牙已打开"
            RESULT_CANCELED -> "用户拒绝打开蓝牙"
            else -> "异常：无法识别的结果代码 $resultCode"
        }
        appendMessage(text)
    }
}
