package com.tq.bluetoothdemo

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.bluetooth.*
import android.content.*
import android.bluetooth.*

class BluetoothActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent:Intent) {
        
        when (intent.action) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> onBluetoothStateChanged(context, intent)
            BluetoothDevice.ACTION_FOUND -> onBluetoothDeviceFound(context, intent)
        }
    }

    fun onBluetoothDeviceFound(context: Context, intent: Intent) {
        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE);
        notice(context, "name: $device.name, address: $device.address")
    }

    fun onBluetoothStateChanged(context: Context, intent: Intent) {
        notice(
            context,
            when(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
                BluetoothAdapter.STATE_TURNING_ON -> "正在启动蓝牙"
                BluetoothAdapter.STATE_ON -> "蓝牙已启动"
                BluetoothAdapter.STATE_TURNING_OFF -> "正在关闭蓝牙"
                BluetoothAdapter.STATE_OFF -> "蓝牙已关闭"
                else -> "未知状态"
            }
        )
    }

    fun notice(context: Context, text: String) {
        AppDatabase.initialize(context)
        Repository.initialize(AppDatabase.instance)
        CoroutineScope(Main + Job()).launch {
            Repository.instance.insertNotice(Notice(0L, System.currentTimeMillis(), text))
        }

        // val activityIntent = Intent(context, MainActivity::class.java)
        // activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // activityIntent.putExtra(BluetoothNotice, text)
        // context.startActivity(activityIntent)
    }
}
