package com.tq.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tq.abc.Manager
import android.webkit.WebView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wv1 = findViewById<WebView>(R.id.webview1)
        val wv2 = findViewById<WebView>(R.id.webview2)
        val wv3 = findViewById<WebView>(R.id.webview3)
        
        wv1.loadData("HELLO", "text/plain", "UTF-8")
        wv1.getSettings().setTextZoom(50);
        
        wv2.loadData("HELLO", "text/plain", "UTF-8")

        wv3.loadData("HELLO", "text/plain", "UTF-8")
        wv3.getSettings().setTextZoom(200);
    }
}
